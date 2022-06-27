package com.example.dodopizzapractice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dodopizzapractice.databinding.ActivityItemBinding
import com.example.dodopizzapractice.databinding.ActivityMainBinding

class ItemActivity : AppCompatActivity() {
    private var _binding: ActivityItemBinding? = null
    private val binding get() = _binding!!


    private var ingredientsList: MutableList<Food>? = null
    private val adapter = IngredientsAdapter()

    private var currentCategory: Int = -1
    private var currentPosition: Int = -1

    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult? ->
        if (result!!.resultCode == Activity.RESULT_OK) {
            var updatedElement = result.data!!.extras!!.getInt("ELEMENT_POSITION", 0)
            getChildData(updatedElement)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val foodItem: ImageView = findViewById(R.id.pizzaImage)
//        val nameItem: TextView = findViewById(R.id.name)
//        val descriptionItem: TextView = findViewById(R.id.description)
//        val priceItem: TextView = findViewById(R.id.price)
//        val backButton: ImageView = findViewById(R.id.backButton)
//        val ingredients: RecyclerView = findViewById(R.id.comboChoiceRV)

        val bundle = intent.extras?.get("PIZZA") as Food
        val image = bundle!!.imageId
        val name = bundle.name
        val description = bundle.description
        val price = bundle.price
//        val position = bundle.getInt("PIZZAPOSITION")

        binding.pizzaImage.setImageResource(image)
        binding.name.text = name
        binding.description.text = description
        binding.price.text = "Добавить в корзину за ${price.toString()},00c"

        binding.backButton.setOnClickListener {
            finish()
        }
        ingredientsList = bundle.ingredients?.toMutableList()


        adapter.submitList(ingredientsList ?: emptyList())
        binding.comboChoiceRV.adapter = adapter

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.comboChoiceRV.layoutManager = layoutManager



        adapter.onItemClick = { category, b ->

            val intent = Intent(this, IngredientsViewPage::class.java)
            intent.putExtra("CATEGORY", category)
            intent.putExtra("POSITION", b)

            currentCategory = category
            currentPosition = b

            activityResultLauncher.launch(intent)
        }

    }

    private fun getChildData(updatedElement: Int) {
        val listFood = DataSource().getList(currentCategory)

        ingredientsList!![currentPosition] = listFood[updatedElement]
        adapter.submitList(ingredientsList!!)
    }


    private fun getIngredients(position: Int): List<Food>? {
        val data = DataSource().comboList()
        return data[position].ingredients
    }
}
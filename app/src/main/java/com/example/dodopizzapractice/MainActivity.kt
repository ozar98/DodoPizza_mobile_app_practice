package com.example.dodopizzapractice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dodopizzapractice.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    //Recycler view
    private lateinit var categoriesList: RecyclerView
    //ViewModel
    private val viewModel=ViewModel()

    private val foodCategoryAdapter = FoodCategoryAdapter()
    private var foodAdapter = FoodAdapter(emptyList())


    private var chooseCityBottomSheet: BottomSheetDialog? = null
    private var aboutBannerBottomSheet: BottomSheetDialog? = null


    private lateinit var foods: RecyclerView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategoriesList()
        setupCityBottomSheet()
        setupBannerBottomSheet()

        binding.cityName.text = "Душанбе"

        binding.text1.setOnClickListener {
            binding.text2.setBackgroundResource(R.color.darkGray)
            binding.address2.visibility = View.INVISIBLE
            binding.address1.visibility = View.VISIBLE
            binding.text1.setBackgroundResource(R.drawable.button_shape)
        }
        binding.text2.setOnClickListener {
            binding.text2.setBackgroundResource(R.drawable.button_shape)
            binding.text1.setBackgroundResource(R.color.darkGray)
            binding.address1.visibility = View.INVISIBLE
            binding.address2.visibility = View.VISIBLE
        }

        binding.cityName.setOnClickListener {
            chooseCityBottomSheet?.show()
        }

        // method for calling categories recycler view

        /// method for calling chosen category and calling its foods
        setupFoodList(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun refreshCategory(categorySelectedID:Int){
        val currentCategories=viewModel.categoryList
        for (item in viewModel.categoryList) {
            item.isSelected = item.id == categorySelectedID
            }
        viewModel.updateCategory(currentCategories)
        foodCategoryAdapter.submitList(currentCategories)

        setupFoodList(categorySelectedID)
    }
    private fun setupListeners(){
        foodCategoryAdapter.onItemClick={ refreshCategory(it)}

    }

    private fun setupCategoriesList() {
        categoriesList = findViewById(R.id.categories_list)
        val categories = viewModel.getCategory()
        setupListeners()
        foodCategoryAdapter.submitList(categories)
        categoriesList.adapter = foodCategoryAdapter
    }

    private fun setupFoodListener(myList:List<Food>){
        foodAdapter.setOnItemClickListener(object: FoodAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, ItemActivity::class.java)

                intent.putExtra("NAME", myList[position].name)
                intent.putExtra("DESCRIPTION", myList[position].description)
                intent.putExtra("PRICE", myList[position].price)
                intent.putExtra("IMAGE", myList[position].imageId)
                intent.putExtra("PIZZAPOSITION", position)
                startActivity(intent)
            }

        })

    }

    private fun setupFoodList(categoryId: Int) {
        val data=viewModel.data
        data.category = categoryId
        foods = findViewById(R.id.foods_list_rv)
        val myList = data.getList()
        foodAdapter = FoodAdapter(myList)
        setupFoodListener(myList)
        foods.adapter = foodAdapter


    }

    private fun setupCityBottomSheet() {
        chooseCityBottomSheet = BottomSheetDialog(this)
        chooseCityBottomSheet?.setContentView(R.layout.bottom_sheet_cities)
        val textInBottomSheet = chooseCityBottomSheet?.findViewById<TextView>(R.id.city_Dushanbe)
        textInBottomSheet?.setOnClickListener {
            val result = textInBottomSheet.text.toString() // это обработчик нажатия на
            // на элемент в bottomSheet

            binding.cityName.text = result
            chooseCityBottomSheet?.dismiss()// этот вызов скроет bottomSheet
        }

        val text2InBottomSheet = chooseCityBottomSheet?.findViewById<TextView>(R.id.city_Hissar)
        text2InBottomSheet?.setOnClickListener {
            val result = text2InBottomSheet.text.toString() // это обработчик нажатия на
            // на элемент в bottomSheet

            binding.cityName.text = result
            chooseCityBottomSheet?.dismiss()// этот вызов скроет bottomSheet
        }

        val text3InBottomSheet = chooseCityBottomSheet?.findViewById<TextView>(R.id.city_Khujand)
        text3InBottomSheet?.setOnClickListener {
            val result = text3InBottomSheet.text.toString() // это обработчик нажатия на
            // на элемент в bottomSheet

            binding.cityName.text = result
            chooseCityBottomSheet?.dismiss()// этот вызов скроет bottomSheet
        }

        val text4InBottomSheet = chooseCityBottomSheet?.findViewById<TextView>(R.id.city_Kulob)
        text4InBottomSheet?.setOnClickListener {
            val result = text4InBottomSheet.text.toString() // это обработчик нажатия на
            // на элемент в bottomSheet

            binding.cityName.text = result
            chooseCityBottomSheet?.dismiss()// этот вызов скроет bottomSheet
        }
    }

    private fun setupBannerBottomSheet() {
        aboutBannerBottomSheet = BottomSheetDialog(this)
        aboutBannerBottomSheet?.setContentView(R.layout.bottom_sheet_about_banner)
        var cardView1 = findViewById<CardView>(R.id.cardView1)
        cardView1?.setOnClickListener {
            aboutBannerBottomSheet?.show()
        }
    }
}
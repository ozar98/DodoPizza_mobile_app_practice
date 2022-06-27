package com.example.dodopizzapractice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dodopizzapractice.databinding.ActivityIngredientsViewPageBinding
import com.example.dodopizzapractice.databinding.ActivityItemBinding

class IngredientsViewPage : AppCompatActivity() {

    private var _binding: ActivityIngredientsViewPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIngredientsViewPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent

        val category = intent.getIntExtra("CATEGORY", 0)
        val pos = intent.getIntExtra("POSITION", 0)

        val adapter = ComboAdapter()
        val ingredientsComboList = getIngredients(category)
        adapter.submitList(ingredientsComboList)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.ingredientsChoice.adapter = adapter
        binding.ingredientsChoice.layoutManager = layoutManager
        binding.ingredientsChoice.scrollToPosition(pos)

        binding.numberChoice.text =
            "${pos+1} / ${ingredientsComboList.size}"

        binding.ingredientsChoice.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState==RecyclerView.SCROLL_STATE_IDLE && layoutManager.findLastCompletelyVisibleItemPosition() >= 0){
                    binding.numberChoice.text =
                        "${layoutManager.findFirstVisibleItemPosition()+1} / ${ingredientsComboList.size}"
                    binding.numberChoice.text =
                        "${layoutManager.findLastCompletelyVisibleItemPosition()+1} / ${ingredientsComboList.size}"
                }
            }
        })


        adapter.onItemClick = { elementPosition ->
            val intent = Intent()
            var position_element = elementPosition

            intent.putExtra("ELEMENT_POSITION", position_element)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.backIngredientsButton.setOnClickListener {
            finish()
        }


    }

    fun getIngredients(category: Int): List<Food> {
        return DataSource().getList(category)
    }

}
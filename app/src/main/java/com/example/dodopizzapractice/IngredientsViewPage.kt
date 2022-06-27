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

class IngredientsViewPage : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var currrentPositionAapter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients_view_page)

        recyclerView = findViewById(R.id.ingredients_choice)
        val backButton: ImageButton = findViewById(R.id.back_ingredients_button)


        val intent = intent

        val category = intent.getIntExtra("CATEGORY", 0)
        val pos = intent.getIntExtra("POSITION", 0)

        val adapter = ComboAdapter()
        val ingredientsComboList = getIngredients(category)
        adapter.submitList(ingredientsComboList)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.scrollToPosition(pos)

        currrentPositionAapter = pos + 1

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState==RecyclerView.SCROLL_STATE_IDLE && layoutManager.findLastCompletelyVisibleItemPosition() >= 0){
                    findViewById<TextView>(R.id.number_choice).text =
                        "${layoutManager.findFirstVisibleItemPosition()+1} / ${ingredientsComboList.size}"
                    findViewById<TextView>(R.id.number_choice).text =
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

        backButton.setOnClickListener {
            finish()
        }


    }


    fun getIngredients(category: Int): List<Food> {
        DataSource().category = category
        return DataSource().getList()
    }

}
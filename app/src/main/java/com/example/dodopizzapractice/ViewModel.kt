package com.example.dodopizzapractice

import androidx.lifecycle.ViewModel

//VIEW MODEL
class MainViewModel : ViewModel() {

    private val dataSource = DataSource()

    var categories = mutableListOf(
        FoodCategory(1, "Комбо", true),
        FoodCategory(2, "Закуски", false),
        FoodCategory(3, "Напитки", false),
        FoodCategory(4, "Пицца", false),
        FoodCategory(5, "Десерты", false),
        FoodCategory(6, "Соусы", false),
        FoodCategory(7, "Другие товары", false),
    )

    fun updateCategories(newCategories: List<FoodCategory>) {
        categories = newCategories.toMutableList()
    }

    fun getFoodById(id: Int = 1): List<Food> {
        return dataSource.getList(id)
    }

    override fun onCleared() {
        super.onCleared()
    }
}

class ItemViewModel: ViewModel(){
    private val dataSource = DataSource()
}
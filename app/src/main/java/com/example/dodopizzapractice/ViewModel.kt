package com.example.dodopizzapractice

class ViewModel {
    val data = DataSource()
    var categoryList = getCategory()
    public fun getCategory(): MutableList<FoodCategory> {
        val listButtonText = arrayListOf<String>(
            "Комбо",
            "Закуски",
            "Напитки",
            "Пицца",
            "Десерты",
            "Соусы",
            "Другие товары"
        )
        val categoryList: MutableList<FoodCategory> = mutableListOf()
        for (index in listButtonText.indices){
            categoryList.add(FoodCategory(index+1,listButtonText[index],index==0))
        }
        return categoryList
    }

    public fun updateCategory(updateCategoryList:MutableList<FoodCategory>){
        categoryList=updateCategoryList
    }




}
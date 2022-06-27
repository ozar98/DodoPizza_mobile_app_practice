package com.example.dodopizzapractice

import java.io.Serializable

data class Food(val imageId: Int,
           val name: String,
           val description: String,
           val price: Int,
           val category: Int,
           val ingredients:List<Food>? =null):Serializable

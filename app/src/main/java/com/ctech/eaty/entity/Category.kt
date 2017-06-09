package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName


data class Category(val id: Int, val name: String, val color: String, @SerializedName("item_name") val itemName: String)
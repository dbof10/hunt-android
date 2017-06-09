package com.ctech.eaty.entity

import com.google.gson.annotations.SerializedName

data class Products(@SerializedName("posts")val products: List<Product>)
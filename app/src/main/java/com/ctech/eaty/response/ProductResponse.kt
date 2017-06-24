package com.ctech.eaty.response

import com.ctech.eaty.entity.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(@SerializedName("posts")val products: List<Product>)
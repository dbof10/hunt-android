package com.ctech.eaty.response

import com.ctech.eaty.entity.Product
import com.google.gson.annotations.SerializedName

data class SearchResponse(@SerializedName("hits")val products: List<Product>)
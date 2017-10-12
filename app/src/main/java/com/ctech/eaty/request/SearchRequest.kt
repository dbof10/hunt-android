package com.ctech.eaty.request

import com.ctech.eaty.util.Constants
import com.google.gson.annotations.SerializedName


data class SearchRequest(@SerializedName("params") val params: String) {

    companion object {
        fun makeHeader() = mapOf(
                "User-Agent" to Constants.ALGOLIA_USER_AGENT,
                "X-Algolia-Application-Id" to Constants.ALGOLIA_ID,
                "X-Algolia-API-Key" to Constants.ALGOLIA_KEY)

        fun makeBody(facetFilters: Array<String> = emptyArray(),
                     page: Int = 0,
                     query: String = "") =
                SearchRequest("facetFilters=[${facetFilters.joinToString(separator = ",")}]&page=$page&query=$query")
    }


}
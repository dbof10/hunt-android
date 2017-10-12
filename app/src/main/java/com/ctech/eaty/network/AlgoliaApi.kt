package com.ctech.eaty.network

import com.ctech.eaty.request.SearchRequest
import com.ctech.eaty.response.SearchResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface AlgoliaApi {

    @POST("/1/indexes/Post_production/query")
    fun searchPost(@HeaderMap header: Map<String, String>, @Body request: SearchRequest): Observable<SearchResponse>
}
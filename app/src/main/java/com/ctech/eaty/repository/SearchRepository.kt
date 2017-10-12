package com.ctech.eaty.repository

import com.ctech.eaty.entity.Product
import com.ctech.eaty.error.EmptyElementException
import com.ctech.eaty.network.AlgoliaApi
import com.ctech.eaty.request.SearchRequest
import io.reactivex.Observable

class SearchRepository(private val apiClient: AlgoliaApi) {
    fun searchPost(page: Int, keyword: String): Observable<List<Product>> {
        return apiClient.searchPost(SearchRequest.makeHeader(),
                SearchRequest.makeBody(page = page, query = keyword))
                .map { it.products }
                .flatMap {
                    if (it.isEmpty()) {
                        return@flatMap Observable.error<List<Product>>(EmptyElementException())
                    } else {
                        return@flatMap Observable.just(it)
                    }
                }
    }


}
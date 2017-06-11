package com.ctech.eaty.repository

import com.ctech.eaty.entity.*
import com.ctech.eaty.entity.Collection
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*


interface ProductHuntApi {
    @POST("/v1/oauth/token")
    fun getAccessToken(@Body auth: Authentication): Observable<AccessToken>

    @GET("/v1/posts")
    fun getPosts(@Query("day") date: String): Observable<ResponseBody>

    @GET("/v1/posts/{id}/comments")
    fun getComments(@Path("id") id: String, @Query("per_page") limit: Int, @Query("page") page: Int): Observable<ResponseBody>

    @GET("/v1/collections?search[featured]=true")
    fun getCollections(@Query("per_page") limit: Int, @Query("page") page: Int): Observable<ResponseBody>

    @GET("/collections/{collection-id}")
    fun getCollectionPosts(@Path("collection-id") collectionId: Int): Observable<Collection>
}
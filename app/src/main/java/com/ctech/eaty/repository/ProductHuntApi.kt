package com.ctech.eaty.repository

import com.ctech.eaty.entity.AccessToken
import com.ctech.eaty.entity.Authentication
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*


interface ProductHuntApi {
    @POST("/v1/oauth/token")
    fun getAccessToken(@Body auth: Authentication): Observable<AccessToken>

    @GET("/v1/posts")
    fun getPosts(@Query("day") date: String): Observable<ResponseBody>

    @GET("/v1/posts/{id}")
    fun getProductDetail(@Path("id") id: Int): Observable<ResponseBody>

    @GET("/v1/posts/all")
    fun getProductsByTopic(@Query("search[topic]") id: Int, @Query("per_page") limit: Int, @Query("page") page: Int): Observable<ResponseBody>

    @GET("/v1/posts/{id}/comments")
    fun getComments(@Path("id") id: Int, @Query("per_page") limit: Int, @Query("page") page: Int): Observable<ResponseBody>

    @GET("/v1/collections?search[featured]=true")
    fun getCollections(@Query("per_page") limit: Int, @Query("page") page: Int): Observable<ResponseBody>

    @GET("/v1/topics?search[trending]=true")
    fun getTopics(@Query("per_page") limit: Int, @Query("page") page: Int): Observable<ResponseBody>

    @GET("/v1/posts/{id}/votes")
    fun getVotes(@Path("id") id: Int, @Query("per_page") limit: Int, @Query("page") page: Int): Observable<ResponseBody>

}
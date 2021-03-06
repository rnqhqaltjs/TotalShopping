package com.practice.totalshopping.model

import com.practice.totalshopping.model.ResultGetSearchShopping
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverAPI {
    @GET("v1/search/shop.json")
    fun getSearchShopping(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int? = null,
        @Query("sort") sort: String? = null,
        @Query("start") start: Int? = null
    ): Call<ResultGetSearchShopping>
}
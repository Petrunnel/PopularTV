package com.example.notes.populartv.api

import android.util.Log
import com.example.notes.populartv.models.TvPostDetails
import com.example.notes.populartv.utilits.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PopularTvApi {

    @GET("./tv/popular?language=en-US")
    suspend fun getTop(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: Int,
    ): PopularTvList

    @GET("/3/tv/{tvPostId}")
    suspend fun getDetails(
        @Path("tvPostId") id: Int,
        @Query("api_key") apiKey: String,
    ): TvPostDetails

    @GET("search/tv")
    suspend fun getSearchResults(
        @Query("api_key") apiKey: String,
        @Query("query") searchRequest: String,
        @Query("page") pageNumber: Int,
    ): SearchTvList

    companion object {
        fun create(): PopularTvApi {
            val logger = HttpLoggingInterceptor { Log.d("API", it) }
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PopularTvApi::class.java)
        }
    }

}
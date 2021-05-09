/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.notes.populartv.api

import android.util.Log
import com.example.notes.populartv.MoviePost
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API communication setup
 */
interface MovieApi {

    @GET("/tv/popular?api_key=6b69ba67a66322caedc958e07f550484&language=en-US&page={pageNumber}")
    fun getTop(
            @Path("pageNumber") pageNumber: String,
            @Query("page") currentPage: Int,
            @Query("total_pages") totalPages: Int
    ): Call<ListingResponse>

    class ListingResponse(val data: ListingData)

    class ListingData(
            val result: List<MovieChildrenResponse>,
            val page: Int,
            val total_pages: Int
    )

    data class MovieChildrenResponse(val data: MoviePost)

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        fun create(): MovieApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL.toHttpUrlOrNull()!!)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MovieApi::class.java)
        }
    }
}
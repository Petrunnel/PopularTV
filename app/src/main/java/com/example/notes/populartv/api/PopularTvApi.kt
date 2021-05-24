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
import com.example.notes.populartv.models.TvPostDetails
import com.example.notes.populartv.utilits.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API communication setup
 */
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

    companion object {
        fun create(): PopularTvApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            logger.level = HttpLoggingInterceptor.Level.BODY

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
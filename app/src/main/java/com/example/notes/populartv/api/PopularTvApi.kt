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

import com.example.notes.populartv.models.TvPostDetails
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

}
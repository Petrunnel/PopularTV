/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.example.notes.populartv

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.db.TvPostDatabase
import com.example.notes.populartv.repository.PopularTvRepository
import com.example.notes.populartv.screens.main_fragment.ViewModelFactory

object Injection {

    private fun providePopularTvRepository(context: Context): PopularTvRepository {
        return PopularTvRepository(PopularTvApi.create(), TvPostDatabase.getInstance(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(providePopularTvRepository(context))
    }
}

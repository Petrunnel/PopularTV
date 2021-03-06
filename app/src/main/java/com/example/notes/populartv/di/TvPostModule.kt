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

package com.example.notes.populartv.di

import com.example.notes.populartv.api.PopularTvApi
import com.example.notes.populartv.db.TvPostDatabase
import com.example.notes.populartv.repository.PopularTvRepository
import com.example.notes.populartv.ui.main_fragment.MainPresenter
import com.example.notes.populartv.utilits.APP_ACTIVITY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TvPostModule {

    @Provides
    @Singleton
    fun providePopularTvRepository(): PopularTvRepository {
        return PopularTvRepository(
            PopularTvApi.create(),
            TvPostDatabase.getInstance(APP_ACTIVITY)
        )
    }

    @Provides
    fun provideDetailsPresenter(): MainPresenter {
        return MainPresenter()
    }

}

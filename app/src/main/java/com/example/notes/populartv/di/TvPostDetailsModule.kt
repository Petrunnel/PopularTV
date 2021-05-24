package com.example.notes.populartv.di

import com.example.notes.populartv.repository.TvPostDetailsRepository
import com.example.notes.populartv.ui.details_fragment.DetailsPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object TvPostDetailsModule {

    @Provides
    fun provideTvPostDetailsRepository(): TvPostDetailsRepository {
        return TvPostDetailsRepository()
    }

    @Provides
    fun provideDetailsPresenter(): DetailsPresenter {
        return DetailsPresenter()
    }
}
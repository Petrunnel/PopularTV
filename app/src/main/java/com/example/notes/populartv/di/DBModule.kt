package com.example.notes.populartv.di

import android.content.Context
import androidx.room.Room
import com.example.notes.populartv.db.TvPostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): TvPostDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TvPostDatabase::class.java,
            "TvPosts.db"
        ).build()
    }
}
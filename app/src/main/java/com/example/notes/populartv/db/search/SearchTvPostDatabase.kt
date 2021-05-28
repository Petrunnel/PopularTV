package com.example.notes.populartv.db.search

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.populartv.models.SearchTvPost
import com.example.notes.populartv.models.TvPost

@Database(version = 1, entities = [SearchTvPost::class, SearchRemoteKey::class], exportSchema = false)
abstract class SearchTvPostDatabase : RoomDatabase() {

    abstract fun getTvPostDao(): SearchTvPostDao
    abstract fun getKeysDao(): SearchRemoteKeyDao

    companion object{

        @Volatile

        private var INSTANCE: SearchTvPostDatabase? = null
        fun getInstance(context: Context): SearchTvPostDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SearchTvPostDatabase::class.java, "SearchTvPosts.db"
            )
                .build()
    }

}
package com.example.notes.populartv.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.populartv.models.TvPost

@Database(version = 1, entities = [TvPost::class, RemoteKey::class], exportSchema = false)
abstract class TvPostDatabase : RoomDatabase() {

    abstract fun getTvPostDao(): TvPostDao
    abstract fun getKeysDao(): RemoteKeyDao

    companion object{

        @Volatile

        private var INSTANCE: TvPostDatabase? = null
        fun getInstance(context: Context): TvPostDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TvPostDatabase::class.java, "TvPosts.db"
            )
                .build()
    }

}
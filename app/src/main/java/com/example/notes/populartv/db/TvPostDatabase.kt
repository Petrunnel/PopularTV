package com.example.notes.populartv.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.populartv.models.TvPost

@Database(entities = [TvPost::class, RemoteKey::class], version = 1, exportSchema = false)
abstract class TvPostDatabase : RoomDatabase() {

    abstract fun getTvPostDao(): TvPostDao
    abstract fun getKeysDao(): RemoteKeyDao

    companion object {

        @Volatile
        private var INSTANCE: TvPostDatabase? = null

        fun getInstance(context: Context): TvPostDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TvPostDatabase::class.java,
                    "TvPosts.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
package com.example.notes.populartv.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.populartv.api.TvPost

@Database(version = 1, entities = [TvPost::class, RemoteKey::class])
abstract class TvPostDatabase : RoomDatabase() {

    companion object{
        fun create(context: Context) : TvPostDatabase {
            val databaseBuilder = Room.databaseBuilder(
                context,
                TvPostDatabase::class.java,
                "TvPost.db"
            )
                return databaseBuilder
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
    abstract fun getTvPostDao(): TvPostDao
    abstract fun getKeysDao(): RemoteKeyDao
}
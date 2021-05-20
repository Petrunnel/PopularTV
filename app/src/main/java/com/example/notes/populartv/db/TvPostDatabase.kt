package com.example.notes.populartv.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.populartv.models.TvPost

@Database(version = 1, entities = [TvPost::class, RemoteKey::class], exportSchema = false)
abstract class TvPostDatabase : RoomDatabase() {

    abstract fun getTvPostDao(): TvPostDao
    abstract fun getKeysDao(): RemoteKeyDao

}
package com.example.notes.populartv.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.populartv.api.TvPost

@Database(version = 1, entities = [TvPost::class, RemoteKey::class])
abstract class TvPostDatabase : RoomDatabase() {
    abstract fun getTvPostDao(): TvPostDao
    abstract fun getKeysDao(): RemoteKeyDao
}
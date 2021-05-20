package com.example.notes.populartv.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes.populartv.models.TvPost

@Dao
interface TvPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvPost: List<TvPost>)

    @Query("SELECT * FROM tv_posts")
    fun getAll(): PagingSource<Int, TvPost>

    @Query("DELETE FROM tv_posts")
    suspend fun deleteAll()
}
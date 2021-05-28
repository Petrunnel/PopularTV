package com.example.notes.populartv.db.search

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes.populartv.models.SearchTvPost
import com.example.notes.populartv.models.TvPost

@Dao
interface SearchTvPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvPost: List<SearchTvPost>)

    @Query("SELECT * FROM search_tv_posts")
    fun getAll(): PagingSource<Int, SearchTvPost>

    @Query("SELECT * FROM search_tv_posts WHERE name LIKE :searchRequest")
    fun getSearchResults(searchRequest: String): PagingSource<Int, SearchTvPost>

    @Query("DELETE FROM search_tv_posts")
    suspend fun deleteAll()
}
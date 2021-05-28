package com.example.notes.populartv.db.search

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<SearchRemoteKey>)

    @Query("SELECT * FROM search_remote_keys WHERE tvId = :repoId")
    suspend fun remoteKeysTvPostId(repoId: Int): SearchRemoteKey?

    @Query("DELETE FROM search_remote_keys")
    suspend fun clearRemoteKeys()
}


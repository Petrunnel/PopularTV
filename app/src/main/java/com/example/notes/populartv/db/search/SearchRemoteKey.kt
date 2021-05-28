package com.example.notes.populartv.db.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_remote_keys")
data class SearchRemoteKey(
    @PrimaryKey
    val tvId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
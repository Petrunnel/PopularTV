package com.example.notes.populartv.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PopularTvList(
    val results: List<TvPost>,
    val page: Int,
    val total_pages: Int
)

@Entity(tableName = "tv_posts")
data class TvPost(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("backdrop_path")
    val backdrop_path: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String,
//    @SerializedName("genre_ids")
//    val genreIds: List<Int>,
//    @SerializedName("origin_country")
//    val originCountry: List<String>,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) : Serializable

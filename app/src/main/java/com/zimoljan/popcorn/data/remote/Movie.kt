package com.zimoljan.popcorn.data.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@Entity(
    tableName = "movies",
    indices = [
        Index("imdbPath", unique = true)
    ]
)
@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "actors")
    val actors: List<String>,
    @Json(name = "desc")
    val desc: String,
    @Json(name = "directors")
    val directors: List<String>,
    @Json(name = "genre")
    val genre: List<String>,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "thumb_url")
    val thumbUrl: String,
    @Json(name = "imdb_url")
    val imdbPath: String,
    @Json(name = "name")
    val title: String,
    @Json(name = "rating")
    val rating: Float,
    @Json(name = "year")
    val year: Int?
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
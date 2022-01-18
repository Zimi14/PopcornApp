package com.zimoljan.popcorn.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.zimoljan.popcorn.data.remote.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Insert
    fun addAll(data: List<Movie>)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id: Int): Movie?

    @Transaction
    fun resetDB(data: List<Movie>) {
        deleteAll()
        addAll(data)
    }
}

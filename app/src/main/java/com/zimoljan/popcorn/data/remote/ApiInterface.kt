package com.zimoljan.popcorn.data.remote

import com.zimoljan.popcorn.utils.calladapter.flow.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET


interface ApiInterface {

    @GET("imdb250.json")
    fun getTopMovies(): Flow<Resource<List<Movie>>>
}
package com.zimoljan.popcorn.utils.calladapter.flow


sealed class Resource<T> {

    class Initial<T> : Resource<T>()

    class Loading<T> : Resource<T>()

    data class Success<T>(
        val message: String?,
        val data: T
    ) : Resource<T>()

    data class Error<T>(
        val errorData: String
    ) : Resource<T>()
}

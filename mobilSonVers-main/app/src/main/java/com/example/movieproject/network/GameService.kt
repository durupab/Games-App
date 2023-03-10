package com.example.movieproject.network

import com.example.movieproject.model.GameDetails
import com.example.movieproject.model.GameResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameService {
    @GET("games")
    suspend fun gatherMovies(
        @Query("key") aKey: String,
        @Query("page") pageNumber: Int
    ): GameResults

    @GET("games")
    suspend fun gatherSearchedMovies(
        @Query("key") aKey: String,
        @Query("search") searchKey: String,
        @Query("page") pageNumber: Int
    ): GameResults

    @GET("games/{id}")
    fun gatherDetails(
        @Path("id") idMovie: String,
        @Query("key") aKey: String
    ): Call<GameDetails>
}

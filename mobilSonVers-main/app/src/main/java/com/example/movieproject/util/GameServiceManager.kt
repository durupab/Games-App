package com.example.movieproject.util

import com.example.movieproject.network.GameService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GameServiceManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.rawg.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val gameService: GameService = retrofit.create(GameService::class.java)
}

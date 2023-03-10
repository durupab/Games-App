package com.example.movieproject.model

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("background_image")
    val imageLink: String?,

    @SerializedName("metacritic")
    val meta : String?,

    @SerializedName("genres")
    val genre : List<Genres>

)

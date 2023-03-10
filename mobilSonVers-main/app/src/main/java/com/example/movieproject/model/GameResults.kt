package com.example.movieproject.model

import com.google.gson.annotations.SerializedName

data class GameResults(@SerializedName("next")
                          val totalPage: String,
                       @SerializedName("results")
                          val results: List<Game>
)

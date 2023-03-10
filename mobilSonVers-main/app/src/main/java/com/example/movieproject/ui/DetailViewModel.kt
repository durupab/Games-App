package com.example.movieproject.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieproject.database.FavoriteDatabase
import com.example.movieproject.database.FavoriteRepository
import com.example.movieproject.model.Favorite
import com.example.movieproject.model.GameDetails
import com.example.movieproject.ui.MainFragment.Companion.KEY
import com.example.movieproject.util.GameServiceManager //???????????????
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    private var favoriteRepository: FavoriteRepository
    private var favorites: LiveData<List<Favorite>>
    private val game = MutableLiveData<GameDetails>()

    init {

        val favoriteDao = FavoriteDatabase.getInstance(application).favoriteDao()
        favoriteRepository = FavoriteRepository(favoriteDao)
        favorites = favoriteRepository.getAllFavorites

    }

    fun insertFavorite(favorite: Favorite) {

        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.insertFavorite(favorite)

        }

    }

    fun deleteFavoriteById(id: String) {

        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.deleteFavoriteById(id)

        }

    }

    fun isFavorite(id: String): Boolean {

        var favorite: Favorite?

        runBlocking(Dispatchers.IO) {
            favorite = favoriteRepository.getFavoriteById(id)
        }

        if (favorite == null) return false
        return true

    }



    fun getGameById(id : String) {

        GameServiceManager.gameService.gatherDetails(id,KEY).enqueue(object : Callback<GameDetails> {

            override fun onResponse(call: Call<GameDetails>, response: Response<GameDetails>) {
                var x :Int =0
                println( "IDDD   " +id)
                game.value = response.body()
            }

            override fun onFailure(call: Call<GameDetails>, t: Throwable) {
                Log.e("Error: ", t.message.toString())
            }

        })

    }

    fun observeGame(): LiveData<GameDetails> {
        return game
    }



}
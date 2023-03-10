package com.example.movieproject.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.movieproject.database.FavoriteDatabase
import com.example.movieproject.database.FavoriteRepository
import com.example.movieproject.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavViewModel(application: Application) : AndroidViewModel(application) {


    private var favoriteRepository: FavoriteRepository
    private var favorites: LiveData<List<Favorite>>


    init {

        val favoriteDao = FavoriteDatabase.getInstance(application).favoriteDao()
        favoriteRepository = FavoriteRepository(favoriteDao)
        favorites = favoriteRepository.getAllFavorites

    }


    fun observeFavorites(): LiveData<List<Favorite>> {
        return favorites
    }


    fun deleteFavoriteById(id: String) {

        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.deleteFavoriteById(id)

        }

    }


    fun insertFavorite(fav  : Favorite){
        favoriteRepository.insertFavorite(fav)
    }


}

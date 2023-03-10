package com.example.movieproject.database


import androidx.lifecycle.LiveData
//import com.example.foodapp.data.model.Favorite
import com.example.movieproject.model.Favorite


class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }

    fun deleteFavoriteById(id: String) {
        favoriteDao.deleteFavoriteById(id)
    }

    fun getFavoriteById(id: String): Favorite {
        return favoriteDao.getFavoriteById(id)
    }


    val getAllFavorites: LiveData<List<Favorite>> = favoriteDao.getAllFavorites()

}
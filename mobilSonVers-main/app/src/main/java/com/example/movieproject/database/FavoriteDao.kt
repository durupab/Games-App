package com.example.movieproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
//import com.example.foodapp.data.model.Favorite
import com.example.movieproject.model.Favorite


@Dao
interface FavoriteDao {

    @Insert()
    fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite_table WHERE id = :id")
    fun deleteFavoriteById(id: String)

    @Query("SELECT * FROM favorite_table WHERE id = :id")
    fun getFavoriteById(id: String): Favorite

    @Query("SELECT * FROM favorite_table order by name ASC")
    fun getAllFavorites(): LiveData<List<Favorite>>

}
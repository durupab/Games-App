package com.example.movieproject.model
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_table")
@Parcelize

data class Favorite(

    @PrimaryKey
    val id: Int?,

    val name: String?,

    val imageLink: String?,
    val meta : String?,


): Parcelable
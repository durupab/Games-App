package com.example.movieproject.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieproject.model.Game
import com.example.movieproject.model.GameResults
import com.example.movieproject.util.GameServiceManager
import com.example.movieproject.util.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val gameMutableLiveData = MutableLiveData<List<Game>>()
    val gameLiveData: LiveData<List<Game>> = gameMutableLiveData
    val errorStateLiveData = SingleLiveEvent<String>()
    private var gameList = ArrayList<Game>()

    fun addMoreMovies(movieCollection: GameResults) {
        for (item in movieCollection.results) {
            gameList.add(item)
        }
        gameMutableLiveData.value = gameList
    }

    fun getGames(aKey: String, query: String, pageNumber: Int, clearPage: Boolean) {
        viewModelScope.launch {
            try {
                if (clearPage) {
                    gameList.clear()
                }
                if (query == "") {
                    val collection = GameServiceManager.gameService.gatherMovies(aKey, pageNumber)
                    addMoreMovies(collection)
                } else {
                    val collection = GameServiceManager.gameService.gatherSearchedMovies(aKey, query, pageNumber)
                    addMoreMovies(collection)
                }
            } catch (e: Exception) {
                errorStateLiveData.postValue("Error Encountered")
                Log.e("Error", "Error calling service", e)
            }
        }
    }

    fun getGameId(position: Int): String? {
        return gameList[position].id
    }
}

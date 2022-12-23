package com.joaquinco.androidavanzado.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaquinco.androidavanzado.domain.Repository
import com.joaquinco.androidavanzado.domain.SuperHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _favorites = MutableLiveData<List<SuperHero>>()
    val favorites: LiveData<List<SuperHero>>
        get() = _favorites


    fun getFavorites(){
        viewModelScope.launch {
            val favoritesList = withContext(Dispatchers.IO){
                repository.getFavorites()
            }
            _favorites.value = favoritesList
        }
    }

}
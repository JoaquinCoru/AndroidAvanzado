package com.joaquinco.androidavanzado.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joaquinco.androidavanzado.domain.Repository
import com.joaquinco.androidavanzado.domain.SuperHero
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _favorites = MutableLiveData<List<SuperHero>>()
    val heros: LiveData<List<SuperHero>>
        get() = _favorites


}
package com.joaquinco.androidavanzado.ui.superherolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaquinco.androidavanzado.data.Repository
import com.joaquinco.androidavanzado.domain.SuperHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SuperHeroListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _heros = MutableLiveData<List<SuperHero>>()
    val heros: LiveData<List<SuperHero>>
        get() = _heros


    fun getSuperheros() {
        viewModelScope.launch {
            val superheros = withContext(Dispatchers.IO) {
                repository.getHerosWithCache()
            }
            _heros.value = superheros
        }
    }
}

package com.joaquinco.androidavanzado.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaquinco.androidavanzado.domain.Location
import com.joaquinco.androidavanzado.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableLiveData<DetailState>()
    val state: LiveData<DetailState>
        get() = _state

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>>
        get() = _locations

    fun getHeroDetail(name: String) {
        viewModelScope.launch {
            val detailState = withContext(Dispatchers.IO) {
                repository.getHeroDetail(name)
            }
            _state.value = detailState
        }
    }

    fun getHeroLocations(id: String) {
        viewModelScope.launch {
            val locationsResult = withContext(Dispatchers.IO){
                repository.getLocations(id)
            }
            _locations.value = locationsResult
        }
    }

}
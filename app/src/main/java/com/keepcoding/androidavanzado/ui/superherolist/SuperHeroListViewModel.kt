package com.keepcoding.androidavanzado.ui.superherolist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import com.keepcoding.androidavanzado.data.Repository
import com.keepcoding.androidavanzado.data.RepositoryImpl
import com.keepcoding.androidavanzado.data.local.LocalDataSourceImpl
import com.keepcoding.androidavanzado.data.local.SuperHeroDatabase
import com.keepcoding.androidavanzado.data.mappers.LocalToPresentationMapper
import com.keepcoding.androidavanzado.data.mappers.RemoteToLocalMapper
import com.keepcoding.androidavanzado.data.mappers.RemoteToPresentationMapper
import com.keepcoding.androidavanzado.data.remote.DragonBallAPI
import com.keepcoding.androidavanzado.data.remote.RemoteDataSourceImpl
import com.keepcoding.androidavanzado.domain.SuperHero
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

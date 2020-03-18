package com.udacity.asteroidradar.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.domain.AsteroidListFetcher
import com.udacity.asteroidradar.entities.Asteroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel(), CoroutineScope by CoroutineScope(Dispatchers.Main) {


    private val fetcher = AsteroidListFetcher()

    private val asteroidsLiveData = MutableLiveData<List<Asteroid>>()
    fun asteroidsLiveData(): LiveData<List<Asteroid>> = asteroidsLiveData

    private val progressLiveData = MutableLiveData<Boolean>()
    fun progressLiveData(): LiveData<Boolean> = progressLiveData

    private val errorLiveData = MutableLiveData<String>()
    fun errorLiveData(): LiveData<String> = errorLiveData

    init {
        loadAsteroids()
    }

    private fun loadAsteroids() {
        fetcher.fetch()
            .onStart {
                progressLiveData.postValue(true)
            }
            .catch {
                Timber.e(it)
                progressLiveData.postValue(false)
                errorLiveData.postValue("Something went wrong!!")
            }
            .onEach {
                progressLiveData.postValue(false)
                asteroidsLiveData.postValue(it)
            }
            .launchIn(this)
    }
}
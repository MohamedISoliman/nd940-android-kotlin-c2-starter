package com.udacity.asteroidradar.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.domain.AsteroidListFetcher
import com.udacity.asteroidradar.domain.ImageOfTheDayFetcher
import com.udacity.asteroidradar.entities.Asteroid
import com.udacity.asteroidradar.entities.ImageOfTheDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel(), CoroutineScope by CoroutineScope(Dispatchers.Main) {


    private val asteroidListFetcher = AsteroidListFetcher()
    private val imageOfTheDayFetcher = ImageOfTheDayFetcher()

    private val asteroidsLiveData = MutableLiveData<List<Asteroid>>()
    fun asteroidsLiveData(): LiveData<List<Asteroid>> = asteroidsLiveData

    private val imageOfTheDayLiveData = MutableLiveData<ImageOfTheDay>()
    fun imageOfTheDayLiveData() = imageOfTheDayLiveData

    private val progressLiveData = MutableLiveData<Boolean>()
    fun progressLiveData(): LiveData<Boolean> = progressLiveData

    private val errorLiveData = MutableLiveData<String>()
    fun errorLiveData(): LiveData<String> = errorLiveData

    init {
        loadAsteroidsAndImageData()
    }

    private fun loadAsteroidsAndImageData() {

        val imageDataFlow = flow { emit(imageOfTheDayFetcher.fetch()) }

        asteroidListFetcher.fetch()
            .zip(imageDataFlow) { list, imageOfTheDay ->
                Pair(list, imageOfTheDay)
            }
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
                asteroidsLiveData.postValue(it.first)
                imageOfTheDayLiveData.postValue(it.second)
            }
            .launchIn(this)
    }
}
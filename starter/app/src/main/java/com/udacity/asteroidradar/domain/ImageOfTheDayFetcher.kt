package com.udacity.asteroidradar.domain

import com.udacity.asteroidradar.AppDependencies
import com.udacity.asteroidradar.remote.NasaRemoteApi

/**
 *
 * Created by Mohamed Ibrahim on 3/19/20.
 */
class ImageOfTheDayFetcher(private val remoteApi: NasaRemoteApi = AppDependencies.moshiRemote) {

    suspend fun fetch() = remoteApi.fetchImageOfTheDay()
}
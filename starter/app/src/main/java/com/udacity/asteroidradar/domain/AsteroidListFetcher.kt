package com.udacity.asteroidradar.domain

import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.NasaRemoteApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.remoteFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import java.util.*

/**
 *
 * Created by Mohamed Ibrahim on 3/17/20.
 */

class AsteroidListFetcher(private val remoteApi: NasaRemoteApi = remoteFactory.nasaRemote) {


    fun fetch(): Flow<List<Asteroid>> {
        val today = Calendar.getInstance().time
        val startDate = today.formattedString(Constants.API_QUERY_DATE_FORMAT)
        val nextFormattedDateList = nextFormattedDateList(today)
        return flow {
            emit(
                remoteApi.fetchNasaFeed(
                    startDate, nextFormattedDateList.last()
                )
            )
        }.map {
            parseAsteroidsJsonResult(
                JSONObject(it),
                nextFormattedDateList
            )
        }
    }
}
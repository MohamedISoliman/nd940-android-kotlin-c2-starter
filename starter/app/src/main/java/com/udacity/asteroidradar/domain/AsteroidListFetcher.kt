package com.udacity.asteroidradar.domain

import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.entities.Asteroid
import com.udacity.asteroidradar.local.AppDatabase
import com.udacity.asteroidradar.local.AsteroidsDao
import com.udacity.asteroidradar.remote.NasaRemoteApi
import com.udacity.asteroidradar.remote.parseAsteroidsJsonResult
import com.udacity.asteroidradar.remote.RemoteFactory
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import java.util.*

/**
 *
 * Created by Mohamed Ibrahim on 3/17/20.
 */

class AsteroidListFetcher(
    private val remoteApi: NasaRemoteApi = AppDependencies.remoteApi,
    private val asteroidDao: AsteroidsDao = AppDependencies.database.asteroidDao()
) {

    fun fetch(): Flow<List<Asteroid>> {
        val today = Calendar.getInstance().time
        val startDate = today.formattedString(Constants.API_QUERY_DATE_FORMAT)
        val nextFormattedDateList = nextFormattedDateList(today)

        return flow {
            if (asteroidDao.getRowCount() > 0) {
                asteroidDao.getAsteroidList().collect { emit(it) }
            } else {
                val remoteData = remoteApi.fetchNasaFeed(startDate, nextFormattedDateList.last())
                    .parseAsteroidsJsonResult(
                        nextFormattedDateList
                    )
                asteroidDao.insertAll(remoteData)
                emit(remoteData)
            }
        }
    }
}
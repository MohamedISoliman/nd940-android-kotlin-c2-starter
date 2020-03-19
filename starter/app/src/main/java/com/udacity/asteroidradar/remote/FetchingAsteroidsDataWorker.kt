package com.udacity.asteroidradar.remote

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.domain.AsteroidListFetcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn

/**
 *
 * Created by Mohamed Ibrahim on 3/19/20.
 */
class FetchingAsteroidsDataWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val asteroidListFetcher = AsteroidListFetcher()

    override suspend fun doWork() = coroutineScope {

        try {
            asteroidListFetcher.fetch(forceUpdateFromRemote = true)
                .first()
            return@coroutineScope Result.success()
        } catch (e: Exception) {
            return@coroutineScope Result.failure()
        }
    }
}

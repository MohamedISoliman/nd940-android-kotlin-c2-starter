package com.udacity.asteroidradar

import android.app.Application
import androidx.room.Room
import androidx.work.*
import com.udacity.asteroidradar.local.AppDatabase
import com.udacity.asteroidradar.remote.FetchingAsteroidsDataWorker
import com.udacity.asteroidradar.remote.RemoteFactory
import java.util.concurrent.TimeUnit

/**
 *
 * Created by Mohamed Ibrahim on 3/18/20.
 */

object AppDependencies {


    lateinit var database: AppDatabase
    val moshiRemote = RemoteFactory.createRemote(RemoteFactory.moshiConverter)
    val scalerRemote = RemoteFactory.createRemote(RemoteFactory.scalarsConverterFactory)

    fun init(app: Application) {

        initRoom(app)
        setupFetchWorkerRequest()
    }

    private fun initRoom(app: Application) {
        database = Room.databaseBuilder(
            app,
            AppDatabase::class.java, "AsteroidRadar_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun setupFetchWorkerRequest() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val fetchAsteroidWorkerRequest =
            PeriodicWorkRequestBuilder<FetchingAsteroidsDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueue(fetchAsteroidWorkerRequest)
    }


}
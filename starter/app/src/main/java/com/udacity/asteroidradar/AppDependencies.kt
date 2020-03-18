package com.udacity.asteroidradar

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.local.AppDatabase
import com.udacity.asteroidradar.remote.RemoteFactory

/**
 *
 * Created by Mohamed Ibrahim on 3/18/20.
 */

object AppDependencies {


    lateinit var database: AppDatabase
    val remoteApi = RemoteFactory.nasaRemote

    fun init(app: Application) {
        database = Room.databaseBuilder(
                app,
                AppDatabase::class.java, "AsteroidRadar_database"
            )
            .fallbackToDestructiveMigration()
            .build()

    }


}
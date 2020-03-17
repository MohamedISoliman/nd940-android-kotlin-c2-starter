package com.udacity.asteroidradar

import android.app.Application
import timber.log.Timber

/**
 *
 * Created by Mohamed Ibrahim on 3/17/20.
 */

class AsteroidRadarApp : Application() {


    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
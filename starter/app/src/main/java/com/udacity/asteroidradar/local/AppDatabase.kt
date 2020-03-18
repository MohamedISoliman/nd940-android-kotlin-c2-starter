package com.udacity.asteroidradar.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.entities.Asteroid

/**
 *
 * Created by Mohamed Ibrahim on 3/18/20.
 */

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun asteroidDao(): AsteroidsDao
}

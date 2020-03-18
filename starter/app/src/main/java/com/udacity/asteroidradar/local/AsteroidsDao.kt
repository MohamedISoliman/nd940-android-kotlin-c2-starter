package com.udacity.asteroidradar.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.entities.Asteroid
import kotlinx.coroutines.flow.Flow


/**
 *
 * Created by Mohamed Ibrahim on 3/18/20.
 */
@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM Asteroid ORDER BY date")
    fun getAsteroidList(): Flow<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroid: List<Asteroid>)

    @Query("SELECT COUNT(id) FROM Asteroid")
    suspend fun getRowCount(): Int

}
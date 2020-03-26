package com.udacity.asteroidradar.local

import androidx.room.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.entities.Asteroid
import com.udacity.asteroidradar.formattedString
import com.udacity.asteroidradar.toDate
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.util.*


/**
 *
 * Created by Mohamed Ibrahim on 3/18/20.
 */
@Dao
@TypeConverters(DateConverter::class)
interface AsteroidsDao {

    @Query("SELECT * FROM Asteroid Where date > :fromDate  ORDER BY date")
    fun getAsteroidList(fromDate: Date): Flow<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroid: List<Asteroid>)

    @Query("SELECT COUNT(id) FROM Asteroid")
    suspend fun getRowCount(): Int

}

object DateConverter {

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): Date? {
        return value?.toDate(Constants.API_QUERY_DATE_FORMAT)
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: Date?): String? {
        return date?.formattedString(Constants.API_QUERY_DATE_FORMAT)
    }
}

package com.udacity.asteroidradar.remote

import com.udacity.asteroidradar.entities.Asteroid
import org.json.JSONObject
import kotlin.collections.ArrayList

fun String.parseAsteroidsJsonResult(
    nextFormattedDate: List<String>
): ArrayList<Asteroid> {

    val nearEarthObjectsJson = JSONObject(this).getJSONObject("near_earth_objects")

    val asteroidList = ArrayList<Asteroid>()

    for (formattedDate in nextFormattedDate) {
        val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)

        for (i in 0 until dateAsteroidJsonArray.length()) {
            val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
            val id = asteroidJson.getLong("id")
            val codename = asteroidJson.getString("name")
            val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                .getJSONObject("kilometers").getDouble("estimated_diameter_max")

            val closeApproachData = asteroidJson
                .getJSONArray("close_approach_data").getJSONObject(0)
            val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                .getDouble("kilometers_per_second")
            val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                .getDouble("astronomical")
            val isPotentiallyHazardous = asteroidJson
                .getBoolean("is_potentially_hazardous_asteroid")

            val asteroid = Asteroid(
                id, codename, formattedDate, absoluteMagnitude,
                estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous,
                formattedDate
            )
            asteroidList.add(asteroid)
        }
    }

    return asteroidList
}
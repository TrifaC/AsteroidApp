package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.utils.Constants
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<Asteroid> {
    // After doing the request, the near earth objects contains the object of the asteroid list.
    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")

    // Create a asteroid list to contain the asteroid item.
    val asteroidList = ArrayList<Asteroid>()

    // get the next 7 days
    val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()

    for (formattedDate in nextSevenDaysFormattedDates) {
        //Get the asteroid item according to the date.
        val dateAsteroidJsonArray: JSONArray = nearEarthObjectsJson.getJSONArray(formattedDate)

        // There are asteroids inside the array in a asteroid array objection.
        for (i in 0 until dateAsteroidJsonArray.length()) {
            // The object contains an asteroid.
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

            // Insert the items into the asteroid object.
            val asteroid = Asteroid(id, codename, formattedDate, absoluteMagnitude,
                estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous)
            // Insert the asteroid object into the list.
            asteroidList.add(asteroid)
        }
    }
    return asteroidList
}

private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()
    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    return formattedDateList
}

fun getDatePairString(): Pair<String,String> {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    val startDate = calendar.time
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val endDate = calendar.time
    return Pair(dateFormat.format(startDate), dateFormat.format(endDate))
}
package com.udacity.asteroidradar.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.utils.Constants
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * The method to parse the JSON object.
 *
 * @param jsonResult The result parsed from the Internet.
 * @return a arraylist which will be shown in the main fragment asteroid list.
 * */
fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<Asteroid> {
    // Prepare to sequence the JSON Object.
    val asteroidList = ArrayList<Asteroid>()
    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")
    val dateList = nearEarthObjectsJson.keys()
    val dateListSorted = dateList.asSequence().sorted()
    // Get each date then doing parsing.
    dateListSorted.forEach {
        val key: String = it
        val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(key)
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
                id,
                codename,
                key,
                absoluteMagnitude,
                estimatedDiameter,
                relativeVelocity,
                distanceFromEarth,
                isPotentiallyHazardous
            )
            asteroidList.add(asteroid)
        }
    }
    return asteroidList
}

fun getTodayDateString(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    val today = calendar.time
    return dateFormat.format(today)
}

/**
 * Get the start date and end date pair.
 *
 * @return Pair<startDate, endDate>
 * */
fun getDatePairString(): Pair<String,String> {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    val startDate = calendar.time
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val endDate = calendar.time
    return Pair(dateFormat.format(startDate), dateFormat.format(endDate))
}

/**
 * Check whether the network is available or not.
 *
 * @param context is the XXX
 * @return True -> There is a Internet, False -> Cannot connect to internet.
 * */
fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}
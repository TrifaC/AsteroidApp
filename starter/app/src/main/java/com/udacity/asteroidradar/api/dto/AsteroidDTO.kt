package com.udacity.asteroidradar.api.dto

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.database.DatabaseAsteroidEntity
import org.json.JSONObject

/**
 * The data transfer object is used to parsing responses from the server
 * or formatting objects then sending to server. The DTO should convert to domain objects before
 * using then.
 * */

/**
 * The container class includes have a string which fetch from NASA API.
 * */
@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroidListString: String)

/**
 * Convert Network results to domain objects
 *
 * @return ArrayList of Asteroid will be used in APP UI.
 * */
fun NetworkAsteroidContainer.asDomainModel(): ArrayList<Asteroid> {
    val tmpJSONObject: JSONObject = JSONObject(asteroidListString)
    return parseAsteroidsJsonResult(tmpJSONObject)
}

/**
 * Convert the network object to database objects
 *
 * @return Array of database asteroid entity which will be used in database.
 * */
fun NetworkAsteroidContainer.asDatabaseModel(): Array<DatabaseAsteroidEntity> {
    val tmpJSONObject: JSONObject = JSONObject(asteroidListString)
    val asteroidList = parseAsteroidsJsonResult(tmpJSONObject)
    return asteroidList.map {
        DatabaseAsteroidEntity(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}
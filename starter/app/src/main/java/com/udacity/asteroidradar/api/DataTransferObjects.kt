package com.udacity.asteroidradar.api

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.database.DatabaseAsteroidEntity
import org.json.JSONObject

/**
 * The data transfer object is used to parsing responses from the server
 * or formatting objects then sending to server. The DTO should convert to domain objects before
 * using then
 * */

@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroidListString: String)

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

/** Convert Network results to database objects */
fun NetworkAsteroidContainer.asDomainModel(): ArrayList<Asteroid> {
    val tmpJSONObject: JSONObject = JSONObject(asteroidListString)
    return parseAsteroidsJsonResult(tmpJSONObject)
}

/** Convert the DTO to database objects */
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
package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.data.Asteroid

/**
 * Data class: Store the database entity of asteroid.
 *
 * @param id of database asteroid entity (Primary Key)
 * @param codename of database asteroid entity
 * @param closeApproachDate of database asteroid entity
 * @param absoluteMagnitude of database asteroid entity
 * @param estimatedDiameter of database asteroid entity
 * @param relativeVelocity of database asteroid entity
 * @param distanceFromEarth of database asteroid entity
 * @param isPotentiallyHazardous of database asteroid entity
 *
 * */
@Entity
data class DatabaseAsteroidEntity constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

/**
 * Function to convert List of database asteroid entity to domain objection
 *
 * @return List of [Asteroid]
 * */
fun List<DatabaseAsteroidEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}


package com.udacity.asteroidradar.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * The data class contain a Asteroid data.
 * (Parcelize makes it can be passed between fragment or activity.)
 *
 * @param id of asteroid
 * @param codename of asteroid
 * @param closeApproachDate of asteroid
 * @param absoluteMagnitude of asteroid
 * @param estimatedDiameter of asteroid
 * @param relativeVelocity of asteroid
 * @param distanceFromEarth of asteroid
 * @param isPotentiallyHazardous of asteroid
 * */
@Parcelize
data class Asteroid(
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable
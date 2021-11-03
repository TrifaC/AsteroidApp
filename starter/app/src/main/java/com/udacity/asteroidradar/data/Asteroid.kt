package com.udacity.asteroidradar.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * The data class contain a Asteroid data.
 * (Parcelize makes it can be passed between fragment or activity.)
 * */
@Parcelize
data class Asteroid(
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable
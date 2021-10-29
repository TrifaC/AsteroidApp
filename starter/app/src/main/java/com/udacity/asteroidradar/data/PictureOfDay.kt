package com.udacity.asteroidradar.data

import com.squareup.moshi.Json

/**
 *  The NASA’s image of the day in Main Screen top.
 * */
data class PictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                        val url: String)
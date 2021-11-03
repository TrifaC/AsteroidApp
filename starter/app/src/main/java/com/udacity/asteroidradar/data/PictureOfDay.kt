package com.udacity.asteroidradar.data

import com.squareup.moshi.Json

/**
 *  The NASA’s image of the day in Main Screen top.
 *
 *  @property mediaType The return media type which can be image or video
 *  @property title The short description of the image (Used for talk back)
 *  @property url The url of the image.
 * */
data class PictureOfDay(
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
)
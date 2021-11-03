package com.udacity.asteroidradar.api.dto

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.data.PictureOfDay

/**
 * The data transfer object is used to parsing responses from the server
 * or formatting objects then sending to server. The DTO should convert to domain objects before
 * using then
 * */

@JsonClass(generateAdapter = true)
data class NetworkNASAImageContainer(val pictureOfDay: PictureOfDay)

/** Convert Network results to database objects */

/** Convert the DTO to database objects */

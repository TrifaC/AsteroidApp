package com.udacity.asteroidradar.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.data.PictureOfDay

/**
 * The data transfer object is used to parsing responses from the server
 * or formatting objects then sending to server. The DTO should convert to domain objects before
 * using then
 * */

@JsonClass(generateAdapter = true)
data class NetworkNASAImageContainer(val picture: NetworkPictureOfDay)

@JsonClass(generateAdapter = true)
data class NetworkPictureOfDay(
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "service_version") val serviceVersion: String,
    val title: String,
    val url: String
)

/** Convert Network results to database objects */
fun NetworkNASAImageContainer.asDomainModel(): PictureOfDay {
    return PictureOfDay(
        mediaType = picture.mediaType,
        title = picture.title,
        url = picture.url
    )
}

/** Convert the DTO to database objects */
//fun NetworkNASAImageContainer.asDatabaseModel(): DatabasePictureOfDay {
//    return
//}
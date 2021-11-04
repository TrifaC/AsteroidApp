package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.data.PictureOfDay

@Entity
data class DatabasePictureOfDayEntity constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val mediaType: String,
    val title: String,
    val url: String
)

fun DatabasePictureOfDayEntity.asDomainModel() = PictureOfDay(
    mediaType = this.mediaType,
    title = this.title,
    url = this.url
)
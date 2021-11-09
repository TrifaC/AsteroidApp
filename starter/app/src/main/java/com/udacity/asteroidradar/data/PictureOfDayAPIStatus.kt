package com.udacity.asteroidradar.data

/**
 * The class represent the status of requesting data with PictureOfDay API.
 *
 * @property LOADING fetching Internet data
 * @property ERROR fetching Internet data but error happen
 * @property DONE finish fetching successfully
 * */
enum class PictureOfDayAPIStatus {
    LOADING,
    DONE,
    ERROR,
}
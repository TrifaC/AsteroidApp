package com.udacity.asteroidradar.data

/**
 * The class represent the status of requesting data with Asteroid API.
 *
 * @property LOADING fetching Internet data
 * @property ERROR fetching Internet data but error happen
 * @property DONE finish fetching successfully
 * */
enum class AsteroidAPIStatus {
    LOADING,
    ERROR,
    DONE
}
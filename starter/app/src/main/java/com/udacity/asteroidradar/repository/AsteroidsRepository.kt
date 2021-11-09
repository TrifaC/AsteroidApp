package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidAPI
import com.udacity.asteroidradar.api.dto.NetworkAsteroidContainer
import com.udacity.asteroidradar.api.dto.asDatabaseModel
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.AsteroidAPIFilter
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * The repository hides the complexity of managing the interactions between the database and the
 * networking code.
 * */
class AsteroidsRepository(private val database: AsteroidDatabase) {

    /**
     * The list of asteroid will be shown in the screen which getting from database.
     * */
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) { it.asDomainModel() }

    /**
     * Get data from Internet and refresh the asteroid stored in the offline cache.
     * */
    suspend fun refreshAsteroid() {
        withContext(Dispatchers.IO) {
            val tmplistResult = AsteroidAPI.retrofitService.getAsteroidsListAsync()
            val networkContainer = NetworkAsteroidContainer(tmplistResult)
            database.asteroidDao.insertAll(*networkContainer.asDatabaseModel())
        }
    }
}
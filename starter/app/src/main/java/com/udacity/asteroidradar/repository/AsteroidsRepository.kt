package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidAPI
import com.udacity.asteroidradar.api.NetworkAsteroidContainer
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * The repository hides the complexity of managing the interactions between the database and the
 * networking code.
 * */
class AsteroidsRepository(private val database: AsteroidDatabase) {

    /**
     * The list of asteroid will be shown in the screen.
     * */
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {it.asDomainModel()}

    /**
     * Refresh the asteroid stored in the offline cache.
     * */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val tmplistResult = AsteroidAPI.retrofitService.getAsteroidsList()
            val networkContainer = NetworkAsteroidContainer(tmplistResult)
            database.asteroidDao.insertAll(*networkContainer.asDatabaseModel())
        }
    }
}
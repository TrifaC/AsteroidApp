package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The DAO give the method for database which can be used to do some interactions.
 * */
@Dao
interface AsteroidDao {
    /**
     * Return the list of asteroids from database.
     * Add live data which make the data can update automatically.
     * */
    @Query ("select * from databaseasteroidentity")
    fun getAsteroids(): LiveData<List<DatabaseAsteroidEntity>>

    /**
     * Insert the asteroid data into local data base.
     * */
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroidEntity)
}

/**
 * The database contains the entities of the asteroids and use the DAO to do interaction.
 * */
@Database(entities = [DatabaseAsteroidEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

/**
 * The method to get the asteroid database.
 * */
fun getDatabase(mContext: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(mContext.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids").build()
        }
    }
    return INSTANCE
}
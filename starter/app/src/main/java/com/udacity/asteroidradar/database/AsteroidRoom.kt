package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


//************************************* DAO ********************************************************


/**
 * The DAO give the method for Asteroid database to interact with UI data and Internet data.
 * */
@Dao
interface AsteroidDao {
    /**
     * Return the list of asteroids from database.
     *
     * @return a List of database Asteroid Entity which is a LiveData.
     * */
    @Query("select * from databaseasteroidentity")
    fun getAsteroids(): LiveData<List<DatabaseAsteroidEntity>>

    /**
     * Insert the asteroid data into local data base.
     *
     * @param asteroids will be inserted into database asteroid list.
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroidEntity)
}


//************************************* Database ***************************************************


/**
 * The database contains the entities of the asteroids and use the DAO to do interaction.
 * */
@Database(entities = [DatabaseAsteroidEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

/**
 * Get method for asteroid database.
 *
 * @param mContext Context
 * @return Asteroid Database
 * */
fun getDatabase(mContext: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                mContext.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids"
            ).build()
        }
    }
    return INSTANCE
}
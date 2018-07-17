package com.marshallaf.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.marshallaf.cache.db.ConfigurationConstants
import com.marshallaf.cache.model.Configuration
import io.reactivex.Flowable

@Dao
abstract class ConfigurationDao {

  @Query(ConfigurationConstants.QUERY_CONFIG)
  abstract fun getConfiguration(): Flowable<Configuration>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertConfiguration(configuration: Configuration)
}

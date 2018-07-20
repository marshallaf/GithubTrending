package com.marshallaf.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.marshallaf.cache.db.ConfigurationConstants

@Entity(tableName = ConfigurationConstants.TABLE_NAME)
data class Configuration(@PrimaryKey val lastCacheTime: Long)

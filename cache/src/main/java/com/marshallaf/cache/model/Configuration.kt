package com.marshallaf.cache.model

import android.arch.persistence.room.Entity
import com.marshallaf.cache.db.ConfigurationConstants

@Entity(tableName = ConfigurationConstants.TABLE_NAME)
class Configuration(val lastCacheTime: Long)

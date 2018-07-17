package com.marshallaf.cache.test.factory

import com.marshallaf.cache.model.Configuration

object ConfigurationDataFactory {

  fun makeCachedConfiguration(): Configuration {
    return Configuration(DataFactory.randomLong())
  }
}

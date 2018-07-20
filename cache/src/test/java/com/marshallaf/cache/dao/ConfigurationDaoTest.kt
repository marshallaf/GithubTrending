package com.marshallaf.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.marshallaf.cache.db.ProjectsDatabase
import com.marshallaf.cache.test.factory.ConfigurationDataFactory
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class ConfigurationDaoTest {

  @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var database: ProjectsDatabase

  @BeforeTest fun setUp() {
    database = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getTargetContext(),
        ProjectsDatabase::class.java)
        .allowMainThreadQueries()
        .build()
  }

  @AfterTest fun tearDown() {
    database.close()
  }

  @Test fun getConfiguration_empty_isEmpty() {
    database.configurationDao().getConfiguration().test().assertNoValues().assertComplete()
  }

  @Test fun insertConfiguration_getConfiguration_nonEmpty_returnsConfiguration() {
    val configuration = ConfigurationDataFactory.makeCachedConfiguration()
    database.configurationDao().insertConfiguration(configuration)

    database.configurationDao().getConfiguration().test().assertValue(configuration).assertComplete()
  }
}
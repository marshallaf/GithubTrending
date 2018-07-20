package com.marshallaf.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.marshallaf.cache.db.ProjectsDatabase
import com.marshallaf.cache.mapper.CachedProjectMapper
import com.marshallaf.cache.test.factory.ProjectDataFactory
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.TimeUnit
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class ProjectsCacheImplTest {

  @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var database: ProjectsDatabase
  private lateinit var mapper: CachedProjectMapper
  private lateinit var projectsCacheImpl: ProjectsCacheImpl

  @BeforeTest fun setUp() {
    database = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getTargetContext(),
        ProjectsDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()

    mapper = CachedProjectMapper()
    projectsCacheImpl = ProjectsCacheImpl(database, mapper)
  }

  @AfterTest fun tearDown() {
    database.close()
  }

  @Test fun clearProjects_completes() {
    projectsCacheImpl.clearProjects().test().assertComplete()
  }

  @Test fun saveProjects_completes() {
    val projects = listOf(ProjectDataFactory.makeProjectEntity())
    projectsCacheImpl.saveProjects(projects).test().assertComplete()
  }

  @Test fun getProjects_returnsExpected() {
    val projects = listOf(ProjectDataFactory.makeProjectEntity())
    projectsCacheImpl.saveProjects(projects).test()

    projectsCacheImpl.getProjects().test().assertValue(projects)
  }

  @Test fun getBookmarkedProjects_returnsExpected() {
    val bookmarked = ProjectDataFactory.makeProjectEntity(true)
    val projects = listOf(ProjectDataFactory.makeProjectEntity(false), bookmarked)
    projectsCacheImpl.saveProjects(projects).test()

    projectsCacheImpl.getBookmarkedProjects().test()
        .assertValue { it.size == 1 }
        .assertValue(listOf(bookmarked))
  }

  @Test fun setProjectBookmarked_setsBookmarkState() {
    val unbookmarked = ProjectDataFactory.makeProjectEntity(false)
    projectsCacheImpl.saveProjects(listOf(unbookmarked)).test()

    projectsCacheImpl.setProjectBookmarked(unbookmarked.id).test()
    projectsCacheImpl.getBookmarkedProjects().test()
        .assertValue { it.size == 1 }
        .assertValue { it[0].isBookmarked }
        .assertValue { it[0].id == unbookmarked.id }
  }

  @Test fun setProjectUnbookmarked_setsBookmarkState() {
    val bookmarked = ProjectDataFactory.makeProjectEntity(true)
    projectsCacheImpl.saveProjects(listOf(bookmarked))

    projectsCacheImpl.setProjectUnbookmarked(bookmarked.id).test()
    projectsCacheImpl.getBookmarkedProjects().test()
        .assertValue { it.isEmpty() }
  }

  @Test fun areProjectsCached_isEmpty_returnsFalse() {
    projectsCacheImpl.areProjectsCached().test().assertValue(false)
  }

  @Test fun areProjectsCached_isNotEmpty_returnsTrue() {
    val projects = List(2) { ProjectDataFactory.makeProjectEntity() }
    projectsCacheImpl.saveProjects(projects).test()

    projectsCacheImpl.areProjectsCached().test().assertValue(true)
  }

  @Test fun isProjectsCacheExpired_isNotExpired_returnsFalse() {
    val lastCache = System.currentTimeMillis()
    projectsCacheImpl.setLastCacheTime(lastCache).test()

    projectsCacheImpl.isProjectsCacheExpired().test().assertValue(false)
  }

  @Test fun isProjectsCacheExpired_isExpired_returnsTrue() {
    val lastCache = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3)
    projectsCacheImpl.setLastCacheTime(lastCache).test().assertComplete()

    projectsCacheImpl.isProjectsCacheExpired().test().assertValue(true)
  }
}

package com.marshallaf.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.marshallaf.cache.db.ProjectsDatabase
import com.marshallaf.cache.test.factory.ProjectDataFactory
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDaoTest {

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

  @Test fun getProjects_empty_returnsEmptyList() {
    database.cachedProjectsDao().getProjects().test()
        .assertValue(emptyList())
  }

  @Test fun getProjects_insertProjects_notEmpty_returnsList() {
    val projects = List(3) { ProjectDataFactory.makeCachedProject() }
    database.cachedProjectsDao().insertProjects(projects)
    database.cachedProjectsDao().getProjects().test()
        .assertValue(projects)
  }

  @Test fun deleteProjects_removesAllProjects() {
    val projects = List(3) { ProjectDataFactory.makeCachedProject() }
    database.cachedProjectsDao().insertProjects(projects)
    database.cachedProjectsDao().getProjects().test().assertValue { !it.isEmpty() }

    database.cachedProjectsDao().deleteProjects()

    database.cachedProjectsDao().getProjects().test().assertValue { it.isEmpty() }
  }

  @Test fun getBookmarkedProjects_getsOnlyBookmarkedProjects() {
    val bookmarked = ProjectDataFactory.makeCachedProject(true)
    val projects = listOf(ProjectDataFactory.makeCachedProject(false), bookmarked)
    database.cachedProjectsDao().insertProjects(projects)
    database.cachedProjectsDao().getProjects().test().assertValue(projects)

    database.cachedProjectsDao().getBookmarkedProjects().test().assertValue(listOf(bookmarked))
  }

  @Test fun setBookmarkedStatus_true_setsBookmarked() {
    val project1 = ProjectDataFactory.makeCachedProject(false)
    val project2 = ProjectDataFactory.makeCachedProject(false)
    database.cachedProjectsDao().insertProjects(listOf(project1, project2))
    database.cachedProjectsDao().setBookmarkedStatus(project1.id, true)

    database.cachedProjectsDao().getProjects().test()
        .assertValue {
          it.all {
            (it.id == project1.id && it.isBookmarked)
                || (it.id == project2.id && !it.isBookmarked)
          }
        }
  }

  @Test fun setBookmarkedStatus_false_setsUnbookmarked() {
    val project1 = ProjectDataFactory.makeCachedProject(true)
    val project2 = ProjectDataFactory.makeCachedProject(true)
    database.cachedProjectsDao().insertProjects(listOf(project1, project2))
    database.cachedProjectsDao().setBookmarkedStatus(project1.id, false)

    database.cachedProjectsDao().getProjects().test()
        .assertValue {
          it.all {
            (it.id == project1.id && !it.isBookmarked)
                || (it.id == project2.id && it.isBookmarked)
          }
        }
  }
}
package com.marshallaf.cache

import com.marshallaf.cache.db.ProjectsDatabase
import com.marshallaf.cache.mapper.CachedProjectMapper
import com.marshallaf.cache.model.CachedProject
import com.marshallaf.cache.model.Configuration
import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.data.repository.ProjectsCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
    private val projectsDatabase: ProjectsDatabase,
    private val mapper: CachedProjectMapper
) : ProjectsCache {

  override fun clearProjects(): Completable {
    return Completable.defer {
      projectsDatabase.cachedProjectsDao().deleteProjects()
      Completable.complete()
    }
  }

  override fun saveProjects(projects: List<ProjectEntity>): Completable {
    return Completable.defer {
      projectsDatabase.cachedProjectsDao().insertProjects(projects.map { mapper.mapToCached(it) })
      Completable.complete()
    }
  }

  override fun getProjects(): Observable<List<ProjectEntity>> {
    return projectsDatabase.cachedProjectsDao().getProjects()
        .toObservable()
        .map { it.map { mapper.mapFromCached(it) } }
  }

  override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
    return projectsDatabase.cachedProjectsDao().getBookmarkedProjects()
        .toObservable()
        .map { it.map { mapper.mapFromCached(it) } }
  }

  override fun setProjectBookmarked(projectId: String): Completable {
    return Completable.defer {
      projectsDatabase.cachedProjectsDao().setBookmarkedStatus(projectId, true)
      Completable.complete()
    }
  }

  override fun setProjectUnbookmarked(projectId: String): Completable {
    return Completable.defer {
      projectsDatabase.cachedProjectsDao().setBookmarkedStatus(projectId, false)
      Completable.complete()
    }
  }

  override fun areProjectsCached(): Single<Boolean> {
    return projectsDatabase.cachedProjectsDao().getProjects()
        .isEmpty
        .map { !it }
  }

  override fun setLastCacheTime(lastCache: Long): Completable {
    return Completable.defer {
      projectsDatabase.configurationDao().insertConfiguration(Configuration(lastCacheTime = lastCache))
      Completable.complete()
    }
  }

  override fun isProjectsCacheExpired(): Single<Boolean> {
    val currentTime = System.currentTimeMillis()
    val expirationTime = TimeUnit.DAYS.toMillis(1)
    return projectsDatabase.configurationDao().getConfiguration()
        .single(Configuration(lastCacheTime = 0))
        .map { currentTime - it.lastCacheTime > expirationTime }
  }
}

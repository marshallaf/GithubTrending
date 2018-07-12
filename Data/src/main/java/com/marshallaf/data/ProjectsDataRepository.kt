package com.marshallaf.data

import com.marshallaf.data.mapper.ProjectMapper
import com.marshallaf.data.repository.ProjectsCache
import com.marshallaf.data.repository.ProjectsDataStore
import com.marshallaf.data.store.ProjectsDataStoreFactory
import com.marshallaf.domain.model.Project
import com.marshallaf.domain.repository.ProjectsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val mapper: ProjectMapper,
    private val cache: ProjectsCache,
    private val factory: ProjectsDataStoreFactory
) : ProjectsRepository {

  override fun getProjects(): Observable<List<Project>> {
    return Observable.zip(
            cache.areProjectsCached().toObservable(),
            cache.isProjectsCacheExpired().toObservable(),
            BiFunction<Boolean, Boolean, ProjectsDataStore> { areProjectsCached, isProjectsCacheExpired ->
                factory.getDataStore(areProjectsCached, isProjectsCacheExpired)
            }
        )
        .flatMap {
          it.getProjects()
        }
        .doOnNext {
          factory.getCacheDataStore()
              .saveProjects(it)
        }
        .map {
          it.map { mapper.mapFromEntity(it) }
        }
  }

  override fun bookmarkProject(projectId: String): Completable {
    return factory.getCacheDataStore().setProjectBookmarked(projectId)
  }

  override fun unbookmarkProject(projectId: String): Completable {
    return factory.getCacheDataStore().setProjectUnbookmarked(projectId)
  }

  override fun getBookmarkedProjects(): Observable<List<Project>> {
    return factory.getCacheDataStore().getBookmarkedProjects()
        .map {
          it.map { mapper.mapFromEntity(it) }
        }
  }
}

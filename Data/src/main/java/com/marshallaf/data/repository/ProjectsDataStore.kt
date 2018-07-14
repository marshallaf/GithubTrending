package com.marshallaf.data.repository

import com.marshallaf.data.model.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface ProjectsDataStore {

  fun getProjects(): Observable<List<ProjectEntity>>

  fun saveProjects(projects: List<ProjectEntity>): Completable

  fun clearProjects(): Completable

  fun getBookmarkedProjects(): Observable<List<ProjectEntity>>

  fun setProjectBookmarked(projectId: String): Completable

  fun setProjectUnbookmarked(projectId: String): Completable
}

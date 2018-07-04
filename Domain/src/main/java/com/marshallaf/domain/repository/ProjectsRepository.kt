package com.marshallaf.domain.repository

import com.marshallaf.domain.model.Project
import io.reactivex.Completable
import io.reactivex.Observable

interface ProjectsRepository {

  fun getProjects(): Observable<List<Project>>

  fun bookmarkProject(projectId: String): Completable

  fun unbookmarkProject(projectId: String): Completable

  fun getBookmarkedProjects(): Observable<List<Project>>
}

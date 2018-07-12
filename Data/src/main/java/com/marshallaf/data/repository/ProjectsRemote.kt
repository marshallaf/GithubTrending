package com.marshallaf.data.repository

import com.marshallaf.data.model.ProjectEntity
import io.reactivex.Observable

interface ProjectsRemote {

  fun getProjects(): Observable<List<ProjectEntity>>
}

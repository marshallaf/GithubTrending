package com.marshallaf.presentation.mapper

import com.marshallaf.domain.model.Project
import com.marshallaf.presentation.model.ProjectModel
import javax.inject.Inject

class ProjectModelMapper @Inject constructor() : Mapper<ProjectModel, Project> {

  override fun mapToModel(data: Project): ProjectModel {
    return ProjectModel(data.id, data.name, data.fullName, data.starCount,
        data.dateCreated, data.ownerName, data.ownerAvatar, data.isBookmarked)
  }

}

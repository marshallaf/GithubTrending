package com.marshallaf.remote.mapper

import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.remote.model.ProjectModel
import javax.inject.Inject

class ProjectsResponseModelMapper @Inject constructor() : ModelMapper<ProjectModel, ProjectEntity> {

  override fun mapFromModel(model: ProjectModel): ProjectEntity {
    return ProjectEntity(model.id ?: "none", model.name ?: "none", model.fullName ?: "none",
        model.starCount?.toString() ?: "none", model.dateCreated ?: "none", model.owner?.ownerName ?: "none",
        model.owner?.ownerAvatar ?: "none")
  }

}

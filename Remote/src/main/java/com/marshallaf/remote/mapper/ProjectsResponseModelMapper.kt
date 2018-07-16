package com.marshallaf.remote.mapper

import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.remote.model.ProjectModel
import com.marshallaf.remote.model.ProjectsResponseModel

class ProjectsResponseModelMapper: ModelMapper<ProjectModel, ProjectEntity> {

  override fun mapFromModel(model: ProjectModel): ProjectEntity {
    return ProjectEntity(model.id, model.name, model.fullName,
    model.starCount.toString(), model.dateCreated, model.owner.ownerName,
    model.owner.ownerAvatar)
  }

}

package com.marshallaf.mobileui.mapper

import com.marshallaf.mobileui.model.UiProject
import com.marshallaf.presentation.model.ProjectModel
import javax.inject.Inject

class ProjectViewMapper @Inject constructor() : ViewMapper<ProjectModel, UiProject> {

  override fun mapToView(presentation: ProjectModel): UiProject {
    return UiProject(presentation.id, presentation.name, presentation.fullName, presentation.starCount,
        presentation.dateCreated, presentation.ownerName, presentation.ownerAvatar, presentation.isBookmarked)
  }
}

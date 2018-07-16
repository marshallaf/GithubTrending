package com.marshallaf.remote.test.factory

import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.remote.model.OwnerModel
import com.marshallaf.remote.model.ProjectModel
import com.marshallaf.remote.model.ProjectsResponseModel

object ProjectDataFactory {

  fun makeOwner() = OwnerModel(DataFactory.randomUuid(), DataFactory.randomUuid())

  fun makeProject() = ProjectModel(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(),
      DataFactory.randomInt(), DataFactory.randomUuid(), makeOwner())

  fun makeProjectEntity() = ProjectEntity(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(),
      DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomBoolean())

  fun makeProjectsResponse() = ProjectsResponseModel(listOf(makeProject(), makeProject(), makeProject()))
}

package com.marshallaf.presentation.test.factory

import com.marshallaf.domain.model.Project
import com.marshallaf.presentation.model.ProjectModel

object ProjectFactory {

  fun makeProjectModel(): ProjectModel {
    return ProjectModel(DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomBoolean())
  }

  fun makeProject(): Project {
    return Project(DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomBoolean())
  }
}

package com.marshallaf.mobileui.test.factory

import com.marshallaf.presentation.model.ProjectModel

object ProjectFactory {

  fun makeProjectModel(): ProjectModel {
    return ProjectModel(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomBoolean())
  }
}

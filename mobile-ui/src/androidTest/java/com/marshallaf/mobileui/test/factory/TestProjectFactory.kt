package com.marshallaf.mobileui.test.factory

import com.marshallaf.domain.model.Project
import com.marshallaf.presentation.model.ProjectModel

object TestProjectFactory {

  fun makeProjectModel(): ProjectModel {
    return ProjectModel(TestDataFactory.randomString(), TestDataFactory.randomString(), TestDataFactory.randomString(), TestDataFactory.randomString(),
        TestDataFactory.randomString(), TestDataFactory.randomString(), TestDataFactory.randomString(), TestDataFactory.randomBoolean())
  }

  fun makeProject(): Project {
    return Project(TestDataFactory.randomString(), TestDataFactory.randomString(), TestDataFactory.randomString(), TestDataFactory.randomString(),
        TestDataFactory.randomString(), TestDataFactory.randomString(), TestDataFactory.randomString(), TestDataFactory.randomBoolean())
  }
}

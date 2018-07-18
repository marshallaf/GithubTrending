package com.marshallaf.cache.test.factory

import com.marshallaf.cache.model.CachedProject
import com.marshallaf.data.model.ProjectEntity

object ProjectDataFactory {

  fun makeCachedProject(): CachedProject {
    return CachedProject(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomBoolean())
  }

  fun makeProjectEntity(): ProjectEntity {
    return ProjectEntity(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomBoolean())
  }

  fun makeBookmarkedProjectEntity(): ProjectEntity {
    return ProjectEntity(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        true)
  }

  fun makeUnbookmarkedProjectEntity(): ProjectEntity {
    return ProjectEntity(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        false)
  }
}

package com.marshallaf.cache.test.factory

import com.marshallaf.cache.model.CachedProject
import com.marshallaf.data.model.ProjectEntity

object ProjectDataFactory {

  fun makeCachedProject(): CachedProject {
    return makeCachedProject(DataFactory.randomBoolean())
  }

  fun makeCachedProject(bookmarked: Boolean): CachedProject {
    return CachedProject(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        bookmarked)
  }

  fun makeProjectEntity(): ProjectEntity {
    return makeProjectEntity(DataFactory.randomBoolean())
  }

  fun makeProjectEntity(bookmarked: Boolean): ProjectEntity {
    return ProjectEntity(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        bookmarked)
  }
}

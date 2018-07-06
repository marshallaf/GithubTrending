package com.marshallaf.domain.test

import com.marshallaf.domain.model.Project
import java.util.*

object ProjectDataFactory {

  fun randomUuid() = UUID.randomUUID().toString()

  fun randomBoolean() = Math.random() < 0.5

  fun makeProject(): Project {
    return Project(randomUuid(), randomUuid(), randomUuid(),
        randomUuid(), randomUuid(), randomUuid(),
        randomUuid(), randomBoolean())
  }

  fun makeProjectList(count: Int): List<Project> {
    val projects = mutableListOf<Project>()
    repeat(count) {
      projects.add(makeProject())
    }
    return projects
  }

}
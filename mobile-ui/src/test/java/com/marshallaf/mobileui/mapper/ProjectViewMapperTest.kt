package com.marshallaf.mobileui.mapper

import com.marshallaf.mobileui.test.factory.ProjectFactory
import org.junit.Assert.*
import kotlin.test.Test

class ProjectViewMapperTest {

  private val mapper = ProjectViewMapper()

  @Test fun mapsToExpectedData() {
    val project = ProjectFactory.makeProjectModel()
    val result = mapper.mapToView(project)

    assertEquals(project.id, result.id)
    assertEquals(project.name, result.name)
    assertEquals(project.fullName, result.fullName)
    assertEquals(project.starCount, result.starCount)
    assertEquals(project.dateCreated, result.dateCreated)
    assertEquals(project.ownerName, result.ownerName)
    assertEquals(project.ownerAvatar, result.ownerAvatar)
    assertEquals(project.isBookmarked, result.isBookmarked)
  }
}

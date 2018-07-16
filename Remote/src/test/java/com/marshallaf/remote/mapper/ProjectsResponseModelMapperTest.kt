package com.marshallaf.remote.mapper

import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.remote.model.ProjectModel
import com.marshallaf.remote.test.factory.ProjectDataFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class ProjectsResponseModelMapperTest {

  private val mapper = ProjectsResponseModelMapper()

  @Test fun mapFromModel_returnsExpected() {
    val input = ProjectDataFactory.makeProject()
    val result = mapper.mapFromModel(input)
    assertEquivalentValues(input, result)
  }

  private fun assertEquivalentValues(expected: ProjectModel, result: ProjectEntity) {
    assertEquals(expected.id, result.id)
    assertEquals(expected.name, result.name)
    assertEquals(expected.fullName, result.fullName)
    assertEquals(expected.starCount.toString(), result.starCount)
    assertEquals(expected.dateCreated, result.dateCreated)
    assertEquals(expected.owner.ownerName, result.ownerName)
    assertEquals(expected.owner.ownerAvatar, result.ownerAvatar)
  }
}

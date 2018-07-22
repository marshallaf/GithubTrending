package com.marshallaf.presentation.mapper

import com.marshallaf.presentation.test.factory.ProjectFactory
import org.junit.Assert.*
import kotlin.test.BeforeTest
import kotlin.test.Test

class ProjectModelMapperTest {

  private lateinit var mapper: ProjectModelMapper

  @BeforeTest fun setUp() {
    mapper = ProjectModelMapper()
  }

  @Test fun mapToModel_mapsToExpected() {
    val input = ProjectFactory.makeProject()
    val result = mapper.mapToModel(input)

    assertEquals(input.id, result.id)
    assertEquals(input.name, result.name)
    assertEquals(input.fullName, result.fullName)
    assertEquals(input.starCount, result.starCount)
    assertEquals(input.dateCreated, result.dateCreated)
    assertEquals(input.ownerName, result.ownerName)
    assertEquals(input.ownerAvatar, result.ownerAvatar)
    assertEquals(input.isBookmarked, result.isBookmarked)
  }
}

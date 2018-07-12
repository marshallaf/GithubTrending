package com.marshallaf.data.mapper

import com.marshallaf.data.model.ProjectEntity
import com.marshallaf.data.test.factory.ProjectFactory
import com.marshallaf.domain.model.Project
import org.junit.Assert.*
import org.junit.Test

class ProjectMapperTest {

  val projectMapper = ProjectMapper()

  @Test fun mapFromEntity() {
    val projectEntity = ProjectFactory.makeProjectEntity()
    assertEqualData(projectEntity, projectMapper.mapFromEntity(projectEntity))
  }

  @Test fun mapToEntity() {
    val project = ProjectFactory.makeProject()
    assertEqualData(projectMapper.mapToEntity(project), project)
  }

  private fun assertEqualData(entity: ProjectEntity, domain: Project) {
    assertEquals(entity.id, domain.id)
    assertEquals(entity.name, domain.name)
    assertEquals(entity.fullName, domain.fullName)
    assertEquals(entity.dateCreated, domain.dateCreated)
    assertEquals(entity.starCount, domain.starCount)
    assertEquals(entity.ownerName, domain.ownerName)
    assertEquals(entity.ownerAvatar, domain.ownerAvatar)
    assertEquals(entity.isBookmarked, domain.isBookmarked)
  }
}

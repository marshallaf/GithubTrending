package com.marshallaf.cache.mapper

import com.marshallaf.cache.test.factory.ProjectDataFactory
import org.junit.Assert.*
import org.junit.Test

class CachedProjectMapperTest {

  private val mapper = CachedProjectMapper()

  @Test fun mapFromCached() {
    val input = ProjectDataFactory.makeCachedProject()
    val result = mapper.mapFromCached(input)

    assertEquals(input.id, result.id)
    assertEquals(input.name, result.name)
    assertEquals(input.fullName, result.fullName)
    assertEquals(input.starCount, result.starCount)
    assertEquals(input.dateCreated, result.dateCreated)
    assertEquals(input.ownerName, result.ownerName)
    assertEquals(input.ownerAvatar, result.ownerAvatar)
    assertEquals(input.isBookmarked, result.isBookmarked)
  }

  @Test fun mapToCached() {
    val input = ProjectDataFactory.makeProjectEntity()
    val result = mapper.mapToCached(input)

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

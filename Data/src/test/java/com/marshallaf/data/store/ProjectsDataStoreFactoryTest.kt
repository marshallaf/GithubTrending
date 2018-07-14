package com.marshallaf.data.store

import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Test

class ProjectsDataStoreFactoryTest {

  private val cacheStore = mock<ProjectsCacheDataStore>()
  private val remoteStore = mock<ProjectsRemoteDataStore>()
  private val factory = ProjectsDataStoreFactory(cacheStore, remoteStore)

  @Test fun getDataStore_cacheExpired_returnsRemoteStore() {
    assertEquals(remoteStore, factory.getDataStore(true, true))
  }

  @Test fun getDataStore_cacheValid_returnsCacheStore() {
    assertEquals(cacheStore, factory.getDataStore(true, false))
  }

  @Test fun getDataStore_noCache_returnsRemoteStore() {
    assertEquals(remoteStore, factory.getDataStore(false, true))
    assertEquals(remoteStore, factory.getDataStore(false, false))
  }

  @Test fun getCacheDataStore_returnsCacheStore() {
    assertEquals(cacheStore, factory.getCacheDataStore())
  }
}

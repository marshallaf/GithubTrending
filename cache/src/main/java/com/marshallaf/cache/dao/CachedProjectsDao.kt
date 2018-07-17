package com.marshallaf.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.marshallaf.cache.db.ProjectConstants
import com.marshallaf.cache.model.CachedProject
import io.reactivex.Flowable

@Dao
abstract class CachedProjectsDao {

  @Query(ProjectConstants.QUERY_PROJECTS)
  abstract fun getProjects(): Flowable<List<CachedProject>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insertProjects(projects: List<CachedProject>)

  @Query(ProjectConstants.DELETE_PROJECTS)
  abstract fun deleteProjects()

  @Query(ProjectConstants.QUERY_BOOKMARKED_PROJECTS)
  abstract fun getBookmarkedProjects(): Flowable<List<CachedProject>>

  @Query(ProjectConstants.QUERY_UPDATE_BOOKMARK_STATUS)
  abstract fun setBookmarkedStatus(projectId: String, isBookmarked: Boolean)
}

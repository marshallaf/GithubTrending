package com.marshallaf.mobileui.model

data class UiProject(
  val id: String, val name: String, val fullName: String, val starCount: String,
  val dateCreated: String, val ownerName: String, val ownerAvatar: String,
  val isBookmarked: Boolean
)
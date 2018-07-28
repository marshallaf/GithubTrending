package com.marshallaf.remote.model

import com.squareup.moshi.Json

class ProjectModel(
    val id: String?,
    val name: String?,
    @Json(name = "full_name") val fullName: String?,
    @Json(name = "stargazers_count") val starCount: Int?,
    @Json(name = "created_at") val dateCreated: String?,
    val owner: OwnerModel?
)

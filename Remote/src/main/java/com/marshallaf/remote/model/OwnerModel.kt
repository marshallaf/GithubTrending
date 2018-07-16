package com.marshallaf.remote.model

import com.squareup.moshi.Json

class OwnerModel(
    @Json(name = "login") val ownerName: String,
    @Json(name = "avatar_url") val ownerAvatar: String
)

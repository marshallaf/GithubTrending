package com.marshallaf.presentation.state

data class Resource<out T> constructor(
    val status: ResourceState,
    val data: T?,
    val message: String?
)

package com.marshallaf.presentation.mapper

interface Mapper<out M, in D> {

  fun mapToModel(data: D): M
}

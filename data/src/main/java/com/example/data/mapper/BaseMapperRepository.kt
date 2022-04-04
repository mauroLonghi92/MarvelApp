package com.example.data.mapper
/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 */
interface BaseMapperRepository<E, D> {

    fun transform(type: E): D

    fun transformToRepository(type: D): E

}

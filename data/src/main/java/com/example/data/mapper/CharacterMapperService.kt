package com.example.data.mapper

import com.example.data.entities.CharacterResponse
import com.example.data.entities.ThumbnailResponse
import com.example.domain.entities.CharacterEntity
import com.example.domain.entities.ThumbnailEntity

open class CharacterMapperService : BaseMapperRepository<CharacterResponse, CharacterEntity> {

    override fun transform(type: CharacterResponse): CharacterEntity =
        CharacterEntity(
            type.id ?:0,
            type.name.orEmpty(),
            type.description.orEmpty(),
            transformToThumbnailEntity(type.thumbnail ?: ThumbnailResponse())
        )

    override fun transformToRepository(type: CharacterEntity): CharacterResponse =
        CharacterResponse(
            type.id,
            type.name,
            type.description
        )

    fun transformToThumbnailEntity(thumbnailResponse: ThumbnailResponse): ThumbnailEntity = ThumbnailEntity(
        thumbnailResponse.path.orEmpty(),
        thumbnailResponse.extension.orEmpty()
    )

    fun transformListOfCharacters(charactersResponse: List<CharacterResponse>): List<CharacterEntity> =
        charactersResponse.map { transform(it) }
}

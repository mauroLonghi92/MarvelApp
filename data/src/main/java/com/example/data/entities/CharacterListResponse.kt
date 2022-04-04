package com.example.data.entities

import com.google.gson.annotations.SerializedName

data class CharacterListResponse(
    @SerializedName("result") val result: List<CharacterResponse>? = emptyList(),
    val offset: Int = 0,
    val limit: Int= 0,
    val total: Int= 0
)


//fun CharacterListResponse.toCharacterListEntity(): List<CharacterEntity> {
//
//    return this.result?.map {
//
//        it.transformToCharacterEntity()
//
//    }.orEmpty()
//}
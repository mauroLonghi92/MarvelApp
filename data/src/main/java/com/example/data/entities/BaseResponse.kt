package com.example.data.entities

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("results") val characters: List<CharacterResponse>,
    val offset: Int = 0,
    val limit: Int = 0,
    val total: Int = 0
)

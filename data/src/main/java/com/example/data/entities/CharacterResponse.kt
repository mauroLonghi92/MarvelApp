package com.example.data.entities

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("thumbnail")
    val thumbnail: ThumbnailResponse? = ThumbnailResponse()
)

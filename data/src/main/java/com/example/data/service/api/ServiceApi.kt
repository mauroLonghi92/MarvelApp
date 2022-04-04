package com.example.data.service.api

import com.example.data.entities.BaseResponse
import com.example.data.entities.CharacterResponse
import com.example.data.entities.MarvelBaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {

    @GET("/v1/public/characters")
    fun getCharacters(): Call<MarvelBaseResponse<BaseResponse<List<CharacterResponse>>>>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacterById(@Path("characterId") id: Int): Call<MarvelBaseResponse<BaseResponse<List<CharacterResponse>>>>

}
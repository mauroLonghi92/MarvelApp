package com.example.data.service

import com.example.data.mapper.CharacterMapperService
import com.example.data.service.api.ServiceApi
import com.example.data.service.generator.ServiceGenerator
import com.example.domain.entities.CharacterEntity
import com.example.domain.service.CharactersService
import com.example.domain.util.Result

class CharactersServiceImpl : CharactersService {

    private val api = ServiceGenerator()
    private val mapper = CharacterMapperService()
    override fun getCharacters(): Result<List<CharacterEntity>> {
        try {
            val callResponse = api.createService(ServiceApi::class.java).getCharacters()
            val response = callResponse.execute()
            if (response.isSuccessful)
                return Result.Success(data = mapper.transformListOfCharacters(response.body()?.data?.characters ?: emptyList()))

        } catch (e: Exception) {
            return Result.Failure(Exception(CHARACTER_NOT_FOUND))
        }
        return Result.Failure(Exception(CHARACTER_NOT_FOUND))
    }

    override fun getCharacterDetail(characterId: Int): Result<CharacterEntity> {
        try {
            val callResponse = api.createService(ServiceApi::class.java).getCharacterById(characterId)
            val response = callResponse.execute()
            if (response.isSuccessful)
                response.body()?.data?.characters?.get(0)?.let { mapper.transform(it) }?.let { return Result.Success(it) }

        } catch (e: Exception) {
            return Result.Failure(Exception(CHARACTER_DETAIL_NOT_FOUND))
        }
        return Result.Failure(Exception(CHARACTER_DETAIL_NOT_FOUND))

    }

    companion object {
        private const val CHARACTER_NOT_FOUND = "Characters not found"
        private const val CHARACTER_DETAIL_NOT_FOUND = "Character Detail not found"
    }
}
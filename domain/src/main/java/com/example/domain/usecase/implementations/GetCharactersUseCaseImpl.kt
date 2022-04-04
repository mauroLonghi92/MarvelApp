package com.example.domain.usecase.implementations

import com.example.domain.entities.CharacterEntity
import com.example.domain.service.CharactersService
import com.example.domain.usecase.GetCharactersUseCase
import com.example.domain.util.Result

class GetCharactersUseCaseImpl(private val charactersService:CharactersService) : GetCharactersUseCase {
    override fun getCharacters(): Result<List<CharacterEntity>> {
        return when (val serviceResult = charactersService.getCharacters()) {
            is Result.Success -> {

                serviceResult
            }
            is Result.Failure -> {
                serviceResult
            }
        }
    }

    override fun getCharacterDetail(characterId: Int): Result<CharacterEntity> {

        return when (val serviceResult = charactersService.getCharacterDetail(characterId)) {
            is Result.Success -> {
                serviceResult
            }
            is Result.Failure -> {
                serviceResult
            }
        }
    }
}
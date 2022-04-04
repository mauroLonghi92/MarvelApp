package com.example.domain.usecase

import com.example.domain.entities.CharacterEntity
import com.example.domain.util.Result
import org.koin.core.KoinComponent

interface GetCharactersUseCase : KoinComponent {
    fun getCharacters(): Result<List<CharacterEntity>>
    fun getCharacterDetail(characterId: Int): Result<CharacterEntity>
}
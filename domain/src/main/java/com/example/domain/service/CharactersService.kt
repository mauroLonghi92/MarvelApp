package com.example.domain.service

import com.example.domain.entities.CharacterEntity
import com.example.domain.util.Result

interface CharactersService {

    fun getCharacters(): Result<List<CharacterEntity>>
    fun getCharacterDetail(characterId: Int): Result<CharacterEntity>
}
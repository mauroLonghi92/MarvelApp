package com.example.di

import com.example.data.service.CharactersServiceImpl
import com.example.domain.service.CharactersService
import com.example.domain.usecase.GetCharactersUseCase
import com.example.domain.usecase.implementations.GetCharactersUseCaseImpl
import org.koin.dsl.module


val serviceModule = module {

    single<CharactersService> { CharactersServiceImpl() }
}

val useCaseModule = module {

    single<GetCharactersUseCase> { GetCharactersUseCaseImpl(get()) }
}

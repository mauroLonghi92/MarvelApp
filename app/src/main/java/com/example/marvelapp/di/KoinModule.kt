package com.example.marvelapp.di

import com.example.marvelapp.viewmodels.CharacterDetailViewModel
import com.example.marvelapp.viewmodels.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CharactersViewModel(get()) }
    viewModel { CharacterDetailViewModel(get()) }
}
package com.example.marvelapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetCharactersUseCase
import com.example.domain.util.Result
import com.example.marvelapp.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel(private val getCharactersUseCase: GetCharactersUseCase) : ViewModel() {

    private val charactersMutableLiveData = MutableLiveData<Event<CharactersData>>()
    val charactersLiveData: LiveData<Event<CharactersData>>
        get() {
            return charactersMutableLiveData
        }

    fun fetchCharacters(): Job = viewModelScope.launch {
        charactersMutableLiveData.postValue(Event(CharactersData(status = CharactersStatus.LOADING)))
        withContext(Dispatchers.IO) { getCharactersUseCase.getCharacters() }.let { result ->
            when (result) {
                is Result.Success -> {
                    charactersMutableLiveData.postValue(
                        Event(
                            CharactersData(
                                status = CharactersStatus.SUCCESS_DATA,
                                data = result.data
                            )
                        )
                    )
                }
                is Result.Failure -> {
                    charactersMutableLiveData.postValue(
                        Event(
                            CharactersData(
                                status = CharactersStatus.ERROR,
                                error = result.exception
                            )
                        )
                    )
                }
            }
        }
    }

    fun onCharacterClicked(characterId: Int) {
        charactersMutableLiveData.value = Event(CharactersData(status = CharactersStatus.OPEN_CHARACTER_DETAIL, data = characterId))
    }
}

data class CharactersData(
    var status: CharactersStatus,
    var data: Any? = null,
    var error: Exception? = null
)

enum class CharactersStatus {
    LOADING,
    SUCCESS_DATA,
    ERROR,
    OPEN_CHARACTER_DETAIL
}
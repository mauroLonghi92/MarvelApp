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

class CharacterDetailViewModel(private val getCharactersUseCase: GetCharactersUseCase) : ViewModel() {

    private val characterDetailMutableLiveData = MutableLiveData<Event<CharacterDetailData>>()
    val characterDetailLiveData: LiveData<Event<CharacterDetailData>>
        get() {
            return characterDetailMutableLiveData
        }

    fun fetchCharacterDetail(characterId: Int): Job = viewModelScope.launch {

        characterDetailMutableLiveData.value = Event(CharacterDetailData(status = CharacterDetailStatus.LOADING))

        when (val result = withContext(Dispatchers.IO) { getCharactersUseCase.getCharacterDetail(characterId) }) {
            is Result.Success -> {
                characterDetailMutableLiveData.postValue(
                    Event(
                        CharacterDetailData(
                            status = CharacterDetailStatus.SUCCESS,
                            data = result.data
                        )
                    )
                )
            }
            is Result.Failure -> {
                characterDetailMutableLiveData.postValue(
                    Event(
                        CharacterDetailData(
                            status = CharacterDetailStatus.ERROR,
                            error = result.exception
                        )
                    )
                )
            }
        }
    }
}

data class CharacterDetailData(
    var status: CharacterDetailStatus,
    var data: Any? = null,
    var error: Exception? = null
)

enum class CharacterDetailStatus {
    LOADING,
    SUCCESS,
    ERROR
}
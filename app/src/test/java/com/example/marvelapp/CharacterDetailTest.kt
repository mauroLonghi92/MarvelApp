package com.example.marvelapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.entities.CharacterEntity
import com.example.domain.service.CharactersService
import com.example.domain.usecase.GetCharactersUseCase
import com.example.domain.usecase.implementations.GetCharactersUseCaseImpl
import com.example.domain.util.Result
import com.example.marvelapp.viewmodels.CharacterDetailData
import com.example.marvelapp.viewmodels.CharacterDetailStatus
import com.example.marvelapp.viewmodels.CharacterDetailViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterDetailTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private lateinit var characterDetailViewModel: CharacterDetailViewModel
    private lateinit var getCharactersUseCase: GetCharactersUseCase
    private val service: CharactersService = mock()


    @Before
    fun init() {
        Dispatchers.setMain(testDispatcher)
        getCharactersUseCase = GetCharactersUseCaseImpl(service)
        characterDetailViewModel = CharacterDetailViewModel(getCharactersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when fetchCharacters return success result with empty list`() {

        whenever(service.getCharacterDetail(CHARACTER_ID)).thenReturn(Result.Success(characterMocked))

        runBlocking {
            characterDetailViewModel.fetchCharacterDetail(CHARACTER_ID).join()
        }

        verify(service).getCharacterDetail(CHARACTER_ID)
    }

    @Test
    fun `when fetchCharacters return success result`() {
        val charactersLiveData = characterDetailViewModel.characterDetailLiveData.testObserver()

        whenever(service.getCharacterDetail(CHARACTER_ID)).thenReturn(Result.Success(characterMocked))

        runBlocking {
            characterDetailViewModel.fetchCharacterDetail(CHARACTER_ID).join()
        }

        verify(service).getCharacterDetail(CHARACTER_ID)

        assertEquals(successResponseList[1].status, charactersLiveData.observedValues[1]?.peekContent()?.status)
        assertEquals(successResponseList[0].status, charactersLiveData.observedValues[0]?.peekContent()?.status)
        assertEquals(successResponseList[0].data, charactersLiveData.observedValues[0]?.peekContent()?.data)
    }

    @Test
    fun `on Save Button Pressed, when barcode is missing`() {

        val exception: Exception = Exception(ERROR_MESSAGE)
        val failureResult: Result.Failure = mock()

        whenever(failureResult.exception).thenReturn(exception)
        whenever(service.getCharacterDetail(CHARACTER_ID)).thenReturn(failureResult)

        runBlocking {
            characterDetailViewModel.fetchCharacterDetail(CHARACTER_ID)
            verify(service).getCharacterDetail(CHARACTER_ID)
        }
    }

    companion object {

        private val characterMocked: CharacterEntity = mock()

        private val successResponseList = listOf(
            CharacterDetailData(status = CharacterDetailStatus.LOADING),
            CharacterDetailData(status = CharacterDetailStatus.SUCCESS, data = characterMocked)
        )

        private const val ERROR_MESSAGE = "Error on test service"
        private const val CHARACTER_ID = 123123
    }
}
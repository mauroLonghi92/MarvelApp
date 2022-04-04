package com.example.marvelapp.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entities.CharacterEntity
import com.example.marvelapp.adapters.CharactersAdapter
import com.example.marvelapp.adapters.OnCharacterClicked
import com.example.marvelapp.databinding.ActivityCharactersBinding
import com.example.marvelapp.fragments.CharacterDetailDialogFragment
import com.example.marvelapp.utils.Event
import com.example.marvelapp.viewmodels.CharactersData
import com.example.marvelapp.viewmodels.CharactersStatus
import com.example.marvelapp.viewmodels.CharactersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersActivity : AppCompatActivity(), OnCharacterClicked {

    private lateinit var binding: ActivityCharactersBinding
    private val charactersViewModel by viewModel<CharactersViewModel>()

    private val charactersAdapter = CharactersAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        charactersViewModel.charactersLiveData.observe(::getLifecycle, ::updateUI)
        charactersViewModel.fetchCharacters()
    }

    private fun updateUI(data: Event<CharactersData>) {
        val mainState = data.getContentIfNotHandled()
        when (mainState?.status) {
            CharactersStatus.LOADING -> {
                binding.charactersActivityLoader.visibility = View.VISIBLE
            }
            CharactersStatus.SUCCESS_DATA -> {
                binding.charactersActivityLoader.visibility = View.GONE
                showCharacterData(data.peekContent().data as List<CharacterEntity>)
            }
            CharactersStatus.ERROR -> {
                binding.charactersActivityLoader.visibility = View.GONE
                showMessage(data.peekContent().error?.message.orEmpty())
            }
            CharactersStatus.OPEN_CHARACTER_DETAIL -> {
                openCharacterDetail(data.peekContent().data as Int)
            }
        }
    }

    private fun showCharacterData(data: List<CharacterEntity>) {

        binding.charactersActivityLoader.visibility = View.GONE
        charactersAdapter.submitList(data)
        binding.charactersActivityRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.charactersActivityRecyclerView.adapter = charactersAdapter
    }

    private fun showMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun openCharacterDetail(characterId: Int) {
        val dialog = CharacterDetailDialogFragment.newInstance(characterId)
        dialog.show(supportFragmentManager, "TAG")
    }

    override fun onCharacterClicked(characterId: Int) {
        charactersViewModel.onCharacterClicked(characterId)
    }
}


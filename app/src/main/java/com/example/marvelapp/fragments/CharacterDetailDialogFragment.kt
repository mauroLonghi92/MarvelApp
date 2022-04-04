package com.example.marvelapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.domain.entities.CharacterEntity
import com.example.marvelapp.databinding.FragmentCharacterDetailBinding
import com.example.marvelapp.extension.getImageByUrl
import com.example.marvelapp.utils.Event
import com.example.marvelapp.viewmodels.CharacterDetailData
import com.example.marvelapp.viewmodels.CharacterDetailStatus
import com.example.marvelapp.viewmodels.CharacterDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentCharacterDetailBinding
    private val characterDetailViewModel by viewModel<CharacterDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCharacterDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterDetailViewModel.characterDetailLiveData.observe(::getLifecycle, ::updateUI)
        characterDetailViewModel.fetchCharacterDetail(arguments?.getInt(EXTRA_CHARACTER_ID) ?: 0)
    }

    private fun updateUI(data: Event<CharacterDetailData>) {
        val mainState = data.getContentIfNotHandled()
        when (mainState?.status) {
            CharacterDetailStatus.LOADING -> {
                binding.characterDetailLoader.visibility = View.GONE
            }
            CharacterDetailStatus.SUCCESS -> {
                binding.characterDetailLoader.visibility = View.GONE
                showCharacterDetail(data.peekContent().data as CharacterEntity)
            }
            CharacterDetailStatus.ERROR -> {
                binding.characterDetailLoader.visibility = View.GONE
                dismiss()
            }
        }
    }

    private fun showCharacterDetail(characterEntity: CharacterEntity) {

        binding.apply {
            this.characterDetailName.text = characterEntity.name
            this.characterDetailDescription.text = characterEntity.description

            val thumbnailImageString = characterEntity.thumbnail.path + "." + characterEntity.thumbnail.extension
            this.characterDetailThumbnail.getImageByUrl(thumbnailImageString)
        }
    }

    companion object {
        fun newInstance(characterId: Int): DialogFragment {
            val args = Bundle()
            args.putInt(EXTRA_CHARACTER_ID, characterId)
            val fragment = CharacterDetailDialogFragment()
            fragment.arguments = args
            return fragment
        }

        private const val EXTRA_CHARACTER_ID: String = "EXTRA_CHARACTER_ID"
    }
}
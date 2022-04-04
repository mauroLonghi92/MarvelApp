package com.example.marvelapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.CharacterEntity
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemCharacterListBinding
import com.example.marvelapp.extension.getImageByUrl

interface OnCharacterClicked {
    fun onCharacterClicked(characterId: Int)
}

class CharactersAdapter (private val onCharacterClicked: OnCharacterClicked) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private val characters = mutableListOf<CharacterEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_character_list,
                parent,
                false
            ),
            onCharacterClicked
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size

    fun submitList(characterList: List<CharacterEntity>) {
        characters.addAll(characterList)
    }

    class ViewHolder(itemView: View, private val onCharacterClicked: OnCharacterClicked) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCharacterListBinding.bind(itemView)
        fun bind(item: CharacterEntity) = with(itemView) {
            itemView.setOnClickListener { onCharacterClicked.onCharacterClicked(item.id) }
            item.let {
                binding.apply {
                    this.characterName.text = it.name
                    this.characterDescription.text = it.description

                    val thumbnailImageString = item.thumbnail.path + "." + item.thumbnail.extension
                    this.characterThumbnail.getImageByUrl(thumbnailImageString)
                }
            }
        }
    }
}
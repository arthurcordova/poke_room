package com.proway.pokemonapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.proway.pokemonapp.R
import com.proway.pokemonapp.databinding.ItemPokemonBinding
import com.proway.pokemonapp.model.PokeTypeSetup
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.utils.toUpperFirstChar

class PokemonAdapterRV :
    RecyclerView.Adapter<PokemonViewHolder>() {

    private var pokemons = mutableListOf<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false).apply {
            return PokemonViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        pokemons[position].apply {
            holder.bind(this)
        }
    }

    override fun getItemCount(): Int = pokemons.size

    fun update(newlist: List<Pokemon>) {
        pokemons.clear()
        pokemons.addAll(newlist)
        notifyDataSetChanged()
    }

}

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: ItemPokemonBinding = ItemPokemonBinding.bind(itemView)

    @SuppressLint("ResourceType")
    fun bind(pokemon: Pokemon) {

        binding.idTextView.text = "#${pokemon.extractIdFromUrl(withPads = true)}"
        binding.nameTextView.text = pokemon.name.toUpperFirstChar()
        pokemon.details?.let {
            Glide.with(itemView.context)
                .load(it.sprites.other.artWork?.image)
                .into(binding.avatarImageView)

            val pokeTypeSetup = it.type[0].type.extractPokeSetup()
            binding.cardItem.setCardBackgroundColor(itemView.context.getColor(pokeTypeSetup.colorCard))
            binding.typesContainer.typeCardView1.setCardBackgroundColor(
                itemView.context.getColor(
                    pokeTypeSetup.colorType
                )
            )
            binding.typesContainer.typeImageView1.setImageDrawable(
                itemView.context.getDrawable(
                    pokeTypeSetup.icon
                )
            )
            binding.typesContainer.typeTextView1.text = it.type[0].type.typeName.toUpperFirstChar()

            if (it.type.size > 1) {
                val setupCard2 = it.type[1].type.extractPokeSetup()
                binding.typesContainer.typeCardView2.setCardBackgroundColor(
                    itemView.context.getColor(
                        setupCard2.colorType
                    )
                )
                binding.typesContainer.typeImageView2.setImageDrawable(
                    itemView.context.getDrawable(
                        setupCard2.icon
                    )
                )
                binding.typesContainer.typeTextView2.text =
                    it.type[1].type.typeName.toUpperFirstChar()
                binding.typesContainer.typeCardView2.visibility = View.VISIBLE
            } else {
                binding.typesContainer.typeCardView2.visibility = View.GONE
            }
        }

    }

}
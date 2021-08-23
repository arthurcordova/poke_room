package com.proway.pokemonapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.proway.pokemonapp.R
import com.proway.pokemonapp.databinding.ItemPokemonBinding
import com.proway.pokemonapp.model.Pokemon

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

    fun bind(pokemon: Pokemon) {


        binding.idTextView.text = "#${pokemon.extractIdFromUrl()}"
        binding.nameTextView.text = pokemon.name

    }

}
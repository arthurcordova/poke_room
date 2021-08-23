package com.proway.pokemonapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.proway.pokemonapp.R
import com.proway.pokemonapp.adapter.PokemonAdapterRV
import com.proway.pokemonapp.databinding.MainFragmentBinding
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.view_model.MainViewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private val adapter = PokemonAdapterRV()

    private val observerPokemons = Observer<List<Pokemon>> { pokemons ->
        adapter.update(pokemons)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.pokemonRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.pokemonRecyclerView.adapter = adapter


        viewModel.pokemons.observe(viewLifecycleOwner, observerPokemons)
        viewModel.fetchAllFromDatabase(requireContext())

    }

}
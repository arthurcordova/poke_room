package com.proway.pokemonapp.view

import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.proway.pokemonapp.R
import com.proway.pokemonapp.adapter.PokemonAdapterRV
import com.proway.pokemonapp.databinding.MainFragmentBinding
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.view.dialogs.FiltersFragment
import com.proway.pokemonapp.view_model.MainViewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private val adapter = PokemonAdapterRV()

    private val observerPokemons = Observer<List<Pokemon>?> { pokemons ->
        adapter.update(pokemons)
    }

    private val observerLoading = Observer<Boolean> { isLoading ->
        binding.progressBar.visibility = if (isLoading) VISIBLE else INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentBinding.bind(view)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.pokemonRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.pokemonRecyclerView.adapter = adapter

        viewModel.pokemons.observe(viewLifecycleOwner, observerPokemons)
        viewModel.isLoading.observe(viewLifecycleOwner, observerLoading)
        viewModel.fetchAllPokemons(requireContext())

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if (it.length > 2) {
                        viewModel.fetchFilteredFromDatabase(requireContext(), it.toString())
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.let {
                    if (it.isEmpty()) {
                        viewModel.fetchAllPokemons(requireContext())
                    }
                }
            }
        })

        binding.buttonFilters.setOnClickListener { showBottomSheetDialog() }

    }

    fun showBottomSheetDialog() {
        val bottomSheet = FiltersFragment.newInstance()
        bottomSheet.show(parentFragmentManager, "dialog_filters")

    }

}
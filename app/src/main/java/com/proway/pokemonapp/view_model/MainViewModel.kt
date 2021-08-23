package com.proway.pokemonapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.proway.pokemonapp.model.Pokemon
import com.proway.pokemonapp.repository.PokemonRepository

class MainViewModel : ViewModel() {

    val _pokeResponse = MutableLiveData<List<Pokemon>>()
    val pokeResponse: LiveData<List<Pokemon>> = _pokeResponse

    val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val pokemonRepository = PokemonRepository()

    fun fetchAllFromServer() {
        pokemonRepository.fetchAll { response, error ->
            response?.let {
                _pokeResponse.value = it.results
            }
            error?.let {
                _error.value = it
            }
        }
    }


}
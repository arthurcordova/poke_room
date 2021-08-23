package com.proway.pokemonapp.services

import com.proway.pokemonapp.model.PokeResponse
import retrofit2.Call
import retrofit2.http.GET

interface PokeService {

    @GET("/api/v2/pokemon/")
    fun getAll(): Call<PokeResponse>

}
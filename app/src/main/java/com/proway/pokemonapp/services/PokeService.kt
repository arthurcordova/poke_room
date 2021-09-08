package com.proway.pokemonapp.services

import com.proway.pokemonapp.model.PokeResponse
import com.proway.pokemonapp.model.PokemonDetails
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {

    @GET("/api/v2/pokemon?limit=200")
    suspend fun getAll(): Response<PokeResponse>

    @GET("/api/v2/pokemon/{id}")
    suspend fun getDetails(@Path("id") id: String): Response<PokemonDetails>

}
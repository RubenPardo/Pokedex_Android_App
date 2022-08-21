package com.example.rparcas.pokedex.data.network

import com.example.rparcas.pokedex.data.model.PokemonListApiResults
import com.example.rparcas.pokedex.data.model.PokemonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApiClient {

    /**
     * "https://pokeapi.co/api/v2/pokemon?limit=20&offset=20"
     */
    @GET("pokemon")
    suspend fun getListPokemon(@Query("limit") limit:Int, @Query("offset")offset:Int): Response<PokemonListApiResults>

    @GET
    suspend fun getInfoPokemon(@Url url:String): Response<PokemonModel>


}
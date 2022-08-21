package com.example.rparcas.pokedex.data

import android.util.Log
import com.example.rparcas.pokedex.data.model.PokemonListApiResults
import com.example.rparcas.pokedex.data.network.PokemonService
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.example.rparcas.pokedex.domain.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api:PokemonService) {


    suspend fun getListPokemon(limit:Int,offset:Int):List<PokemonDomain> {
        val respuesta = api.getListPokemon(limit, offset)
        return respuesta.map { it.toDomain() }
    }


}
package com.example.rparcas.pokedex.data

import android.util.Log
import com.example.rparcas.pokedex.data.model.*
import com.example.rparcas.pokedex.data.network.PokemonService
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.example.rparcas.pokedex.domain.toDomain
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api:PokemonService) {


    suspend fun getListPokemon(limit:Int,offset:Int): PokemonListApiResults {
        return api.getListPokemon(limit, offset)
    }


    suspend fun getInfoPokemon(url:String): PokemonDomain{
        return api.getInfoPokemon(url).toDomain()
    }

}
package com.example.rparcas.pokedex.data

import android.util.Log
import com.example.rparcas.pokedex.data.database.dao.PokemonDao
import com.example.rparcas.pokedex.data.database.entities.PokemonEntity
import com.example.rparcas.pokedex.data.database.entities.toDatabase
import com.example.rparcas.pokedex.data.model.*
import com.example.rparcas.pokedex.data.network.PokemonService
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.example.rparcas.pokedex.domain.toDomain
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val api:PokemonService,
    private val dao:PokemonDao,
    ) {


    suspend fun getListPokemonFromApi(limit:Int, offset:Int): PokemonListApiResults {
        return api.getListPokemon(limit, offset)
    }

    suspend fun getListPokemonFromDB():List<PokemonDomain> {

       return dao.getAllPokemon().map{it.toDomain()}

    }

    suspend fun insertListPokemonFromDB(pokemonList:List<PokemonDomain>) {
        return dao.insertAll(pokemonList.map { it.toDatabase() })
    }


    suspend fun getInfoPokemonFromApi(url:String): PokemonDomain{
        return api.getInfoPokemon(url).toDomain()
    }

    suspend fun getInfoPokemonFromDB(id: Int): PokemonDomain {
        return dao.getPokemonById(id).toDomain()
    }

}
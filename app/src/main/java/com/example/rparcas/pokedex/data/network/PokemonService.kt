package com.example.rparcas.pokedex.data.network

import android.util.Log
import com.example.rparcas.pokedex.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PokemonService  @Inject constructor(private val api:PokemonApiClient){


    /**
     * Obetener la lista de pokemon (se obtiene solo el nombre y su url de info
     * por cada url pedir su info con [getInfoPokemon]
     */
    suspend fun getListPokemon(limit:Int,offset:Int):List<PokemonModel>{
        return withContext(Dispatchers.IO){

            val listaPokemonModel:MutableList<PokemonModel> = arrayListOf()

            val response: Response<PokemonListApiResults> = api.getListPokemon(limit,offset)

            val pokemonListApiResults = response.body() ?: PokemonListApiResults(listOf())

            for (pokemonResult:PokemonResult in pokemonListApiResults.results){
                listaPokemonModel.add(getInfoPokemon(pokemonResult.url))
            }

            listaPokemonModel.toList()

        }
    }

    /**
     * Obtiene la informacion de un pokemon en especifico
     */
    suspend fun getInfoPokemon(url:String):PokemonModel{

        return api.getInfoPokemon(url).body() ?: PokemonModel(-1, PokemonSprites("","",
            OtherSprites(OfficialArtwork(""))
        ),"","", listOf())

    }


}
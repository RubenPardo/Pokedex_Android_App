package com.example.rparcas.pokedex.data.network

import android.util.Log
import com.example.rparcas.pokedex.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PokemonService  @Inject constructor(private val api:PokemonApiClient){


    /**
     * Obetener la lista de pokemon (se obtiene solo el nombre y su url de info
     * por cada url pedir su info con [getInfoPokemon]
     */
    suspend fun getListPokemon(limit:Int,offset:Int): PokemonListApiResults {

        return withContext(Dispatchers.IO){
            val response: Response<PokemonListApiResults> = api.getListPokemon(limit,offset)

            response.body() ?: PokemonListApiResults(listOf())



        }
    }

    /**
     * Obtiene la informacion de un pokemon en especifico
     */
    suspend fun getInfoPokemon(url:String):PokemonModel{

        return withContext(Dispatchers.IO){
            api.getInfoPokemon(url).body() ?: PokemonModel(-1, PokemonSprites("","",
                OtherSprites(OfficialArtwork(""))
            ),"","", listOf(),0,0,0,listOf())

        }

    }


}
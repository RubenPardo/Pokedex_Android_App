package com.example.rparcas.pokedex.domain

import android.util.Log
import com.example.rparcas.pokedex.data.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObtenerListaPokemonAPI @Inject constructor(
    private val pokemonRepository: PokemonRepository
){

    /**
     * Obtener lista de pokemon, primero intentar de la BD, si este devuelve Null es que no hay y
     * por tanto hay que obtenerlo de la API. De la api se va obteniendo poco a poco y se hizo mediante un flow
     * para no bloquear mucho la UI principal
     */
    suspend operator fun invoke(limit:Int,offset:Int): Flow<List<PokemonDomain>> = flow  {


            // obtener de API
            val listPokemonUrls = pokemonRepository.getListPokemonFromApi(limit, offset)

            // emitir cada 20 pokemon
            var cont:Int = 0
            val mutableListPokemon: MutableList<PokemonDomain> = mutableListOf()
            for (url:String in listPokemonUrls.results.map { it.url }){
                // pedir pokemon repositorio
                mutableListPokemon.add(pokemonRepository.getInfoPokemonFromApi(url))
                // a los 20 emitir
                if(cont++ == 20){
                    // return -----
                    emit(mutableListPokemon.toList())
                    // insertarlo en la BD -----------------
                    pokemonRepository.insertListPokemonFromDB(mutableListPokemon.toList())
                    cont = 0
                    mutableListPokemon.clear()
                }

            }

    }


}
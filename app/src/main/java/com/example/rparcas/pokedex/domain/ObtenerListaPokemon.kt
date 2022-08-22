package com.example.rparcas.pokedex.domain

import com.example.rparcas.pokedex.data.PokemonRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObtenerListaPokemon @Inject constructor(
    private val pokemonRepository: PokemonRepository
){

    /**
     * Obtener lista de pokemon, primero intentar de la BD, si este devuelve Null es que no hay y
     * por tanto hay que obtenerlo de la API. De la api se va obteniendo poco a poco y se hizo mediante un flow
     * para no bloquear mucho la UI principal
     */
    suspend operator fun invoke(limit:Int,offset:Int): Flow<List<PokemonDomain>> = flow {

        // obtener de BD

            if(null == null){
                // no hay nada en la BD
                // obtener de API
                val listPokemonUrls = pokemonRepository.getListPokemon(limit, offset)

                // emitir cada 20 pokemon
                var cont:Int = 0
                val mutableListPokemon: MutableList<PokemonDomain> = mutableListOf()
                for (url:String in listPokemonUrls.results.map { it.url }){

                    // pedir pokemon repositorio

                    mutableListPokemon.add(pokemonRepository.getInfoPokemon(url))

                    // a los 20 emitir
                    if(cont++ == 20){
                        // return -----
                        emit(mutableListPokemon.toList())
                        cont = 0
                        mutableListPokemon.clear()
                    }

                }


            }else{
                // hay datos en la BD
                // emitir de la BD

            }
    }


}
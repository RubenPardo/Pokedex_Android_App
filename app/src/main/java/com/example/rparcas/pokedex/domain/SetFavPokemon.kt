package com.example.rparcas.pokedex.domain

import android.util.Log
import com.example.rparcas.pokedex.data.PokemonRepository
import javax.inject.Inject

class SetFavPokemon @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend operator fun invoke(isFav:Boolean, idPokemon:Int){
        Log.d("PRUEBA","SetFavPokemon: se evnia: $isFav al pokemon $idPokemon")
        val columAfected = pokemonRepository.setFavPokemon(isFav,idPokemon)
        Log.d("PRUEBA","SetFavPokemon: columnas afectafas $columAfected")
    }

}
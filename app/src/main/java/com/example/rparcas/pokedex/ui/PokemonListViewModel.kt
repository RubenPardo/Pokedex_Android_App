package com.example.rparcas.pokedex.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rparcas.pokedex.data.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel(){


    fun getListPokemon(limit:Int, offset:Int){
        viewModelScope.launch {
            val respuesta = pokemonRepository.getListPokemon(limit, offset)
            Log.d("PRUEBA", respuesta.toString())
        }
    }


}
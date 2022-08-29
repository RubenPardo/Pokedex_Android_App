package com.example.rparcas.pokedex.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rparcas.pokedex.domain.ObtenerInfoPokemonDB
import com.example.rparcas.pokedex.domain.ObtenerListaPokemonAPI
import com.example.rparcas.pokedex.domain.ObtenerListaPokemonDB
import com.example.rparcas.pokedex.domain.PokemonDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val obtenerInfoPokemon: ObtenerInfoPokemonDB
): ViewModel() {


    val pokemonLiveData = MutableLiveData<PokemonDomain>()


    /**
     *
     * Al crear la activity obtenemos la info del pokemon pasado de la bd
     *
     */
    fun onCreate(idPokemon:Int){

        viewModelScope.launch {
            val pokemon = obtenerInfoPokemon(idPokemon)
            pokemonLiveData.postValue(pokemon)
        }

    }


}
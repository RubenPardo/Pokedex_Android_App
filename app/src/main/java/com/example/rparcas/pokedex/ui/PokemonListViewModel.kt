package com.example.rparcas.pokedex.ui

import android.util.Log
import androidx.core.content.contentValuesOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rparcas.pokedex.data.PokemonRepository
import com.example.rparcas.pokedex.domain.ObtenerListaPokemon
import com.example.rparcas.pokedex.domain.PokemonDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.roundToInt

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val obtenerListaPokemon: ObtenerListaPokemon
): ViewModel(){


    val listPokemon =  MutableLiveData<List<PokemonDomain>>()



    fun getListPokemon(cuantos:Int){

        // PARA AGILIZAR LAS PETICIONES API LANZAMOS DIFERENTES HILOS PIDIENDO 200 POKEMON CADA UNO
        val topePokemon = 200
        val numVeces: Double = ceil(cuantos.toDouble()/topePokemon)// redondear hacia arriba


       (0..numVeces.toInt()).forEach{ i ->

           viewModelScope.launch {
               obtenerListaPokemon(topePokemon,topePokemon*i).collect{
                   listPokemon.postValue(it)
               }

           }
       }


    }
}



package com.example.rparcas.pokedex.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rparcas.pokedex.domain.ObtenerListaPokemonAPI
import com.example.rparcas.pokedex.domain.ObtenerListaPokemonDB
import com.example.rparcas.pokedex.domain.PokemonDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val obtenerListaPokemonAPI: ObtenerListaPokemonAPI,
    private val obtenerListaPokemonDB: ObtenerListaPokemonDB
): ViewModel(){


    val listPokemon =  MutableLiveData<List<PokemonDomain>>()



    fun getListPokemon(cuantos:Int){



        // obtener primero de BD

        viewModelScope.launch {
            val pokemonList = obtenerListaPokemonDB()

            if(pokemonList.isNullOrEmpty()){
                // NO hay nada en la BD, obtener de API REST
                // PARA AGILIZAR LAS PETICIONES API LANZAMOS DIFERENTES HILOS PIDIENDO 200 POKEMON CADA UNO
                val topePokemon = 200
                val numVeces: Double = ceil(cuantos.toDouble()/topePokemon)// redondear hacia arriba


                (0..numVeces.toInt()).forEach{ i ->
                    if(topePokemon*i<cuantos){
                        Log.d("PRUEBA","SE LANZA")
                        viewModelScope.launch {
                            obtenerListaPokemonAPI(topePokemon,topePokemon*i).collect{
                                Log.d("PRUEBA","COLLECT")
                                listPokemon.postValue(it)
                            }

                        }
                    }

                }
            }else{
                // hay datos en la BD, publicar en la vista
                listPokemon.postValue(pokemonList)

            }

        }






    }
}



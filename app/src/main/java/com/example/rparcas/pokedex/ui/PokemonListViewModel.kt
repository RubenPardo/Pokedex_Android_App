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


    val listPokemonLiveData =  MutableLiveData<List<PokemonDomain>>()
    private val listaPokemon = mutableListOf<PokemonDomain>()
    val tipoFiltro1 = MutableLiveData<String?>() // filtro de tipo pokemon 1
    val tipoFiltro2 = MutableLiveData<String?>() // filtro de tipo pokemon 2
    private var nombrePokemonFiltro:String? = null


    /**
     * Obtener los pokemon de la BD y si no hay datos obtenerlos de la API
     * Guarda la lista en la variable [listaPokemon] para poder filtrar luego
     * @param cuantos cuantos pokemon pedir empenzando desde el primero
     * @return lista de pokemon via [listPokemonLiveData]
     */
    fun getListPokemon(cuantos:Int){

        listaPokemon.clear()

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
                                listaPokemon.addAll(it)
                                listPokemonLiveData.postValue(listaPokemon)
                            }

                        }
                    }

                }
            }else{
                // hay datos en la BD, publicar en la vista
                listaPokemon.addAll(pokemonList)
                // de esta forma si se actualiza la vista y se vuelve a pedir, se vuelven a aplicar los filtros
                listPokemonLiveData.postValue(filtrarListaPokemon(tipoFiltro1.value,tipoFiltro2.value,nombrePokemonFiltro))

            }

        }

    }

    /**
     * @param tipo1 tipo pokemon 1 para filtrar la lista, si es null es para quitarlo
     * @return lista de pokemon domain por el live data
     */
    fun filtrarTipo1(tipo1:String?){
         // seteamos valor para que en la vista principal se pinte
        if(tipo1 == tipoFiltro1.value){
            // si selecciona el mismo, lo quitamos
            tipoFiltro1.postValue(null)
            tipoFiltro1.value = null
        }else{
            tipoFiltro1.postValue(tipo1) // esto lanzo un thread para avisar a los observables
            tipoFiltro1.value = tipo1
        }
        listPokemonLiveData.postValue(filtrarListaPokemon(tipoFiltro1.value,tipoFiltro2.value,this.nombrePokemonFiltro))
    }

    /**
     * @param tipo1 tipo pokemon 1 para filtrar la lista, si es null es para quitarlo
     * @return lista de pokemon domain por el live data
     */
    fun filtrarTipo2(tipo2:String?){
        // seteamos valor para que en la vista principal se pinte
        if(tipo2 == tipoFiltro2.value){
            // si selecciona el mismo, lo quitamos
            tipoFiltro2.postValue(null)
            tipoFiltro2.value = null
        }else{
            tipoFiltro2.postValue(tipo2)
            tipoFiltro2.value = tipo2
        }
        listPokemonLiveData.postValue(filtrarListaPokemon(tipoFiltro1.value,tipoFiltro2.value,this.nombrePokemonFiltro))
    }


    /**
     * añade el filtro de nombre (si contiene el nombre)
     * @param nombre del pokemon a filtrar
     * @return lista de [PokemonDomain] via live data
     */
    fun filtrarPorNombre(nombre: String?) {
        this.nombrePokemonFiltro = nombre
        // aplicamos todos los filtros
        listPokemonLiveData.postValue(filtrarListaPokemon(tipoFiltro1.value,tipoFiltro2.value,this.nombrePokemonFiltro))

    }


    /**
     *
     * Filtrar la lista por nombre y por tipos
     *
     * Filtrar la lista de pokemon por tipos, 4 opciones
     * 1. tipo1 = null y tipo2 = null =  todos los pokemon
     * 2. tipo1 = ALGO tipo2 = null =  los pokemon que tienen el tipo 1 y cualquier en el segundo
     * 3. tipo1 = null tipo2 = ALGO =  los pokemon que tienen el tipo 2 y cualquier en el primero
     * 2. tipo1 = ALGO tipo2 = ALGO =  los pokemon que coincidan con esos dos tipos
     *
     * @param tipo1 tipo 1 a filtrar
     * @param tipo2 tipo 2 a filtrar
     * @param nombre nombre a filtrar
     *
     * @return lista de [PokemonDomain] filtrada por los tipos
     *
     */
    private fun filtrarListaPokemon(tipo1:String?, tipo2: String?, nombre:String?):List<PokemonDomain>{
        // filtramos primero por tipo
        var list =
            if(tipo1.isNullOrEmpty() && tipo2.isNullOrEmpty()){
            // Opcion 1
            listaPokemon

        }
            else if (!tipo1.isNullOrEmpty() && tipo2.isNullOrEmpty()){
            // Opcion 2
            listaPokemon.filter { pokemonDomain -> pokemonDomain.tipos[0].type.name == tipo1 }

        }
            else if (tipo1.isNullOrEmpty() && !tipo2.isNullOrEmpty()) {
            // Opcion 3
            val list = listaPokemon.filter { pokemonDomain ->
                // puede que no tenga 2 tipos
                (pokemonDomain.tipos.size > 1 ) && pokemonDomain.tipos[1].type.name == tipo2 }
            list
        }
            else{
            // Opcion 4
            val listPokemonTemp = listaPokemon.filter { pokemonDomain -> pokemonDomain.tipos[0].type.name == tipo1 }
            val list = listPokemonTemp.filter { pokemonDomain ->
                // puede que no tenga 2 tipos
                (pokemonDomain.tipos.size > 1 ) && pokemonDomain.tipos[1].type.name == tipo2 }
            list

        }

        // filtrar por nombre
        list = if(nombre!=null){
            list.filter { it.name.contains(nombre) }
        }else{
            list
        }


        return list

    }



}



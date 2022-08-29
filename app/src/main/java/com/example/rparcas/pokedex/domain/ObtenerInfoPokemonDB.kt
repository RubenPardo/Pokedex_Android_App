package com.example.rparcas.pokedex.domain

import com.example.rparcas.pokedex.data.PokemonRepository
import javax.inject.Inject

class ObtenerInfoPokemonDB @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend operator fun invoke(id:Int): PokemonDomain {
        // obtener de BD
        return pokemonRepository.getInfoPokemonFromDB(id)
    }
}
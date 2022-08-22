package com.example.rparcas.pokedex.domain

import com.example.rparcas.pokedex.data.PokemonRepository
import javax.inject.Inject

class ObtenerListaPokemonDB @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend operator fun invoke(): List<PokemonDomain> {

        // obtener de BD
        return pokemonRepository.getListPokemonFromDB()
    }
}
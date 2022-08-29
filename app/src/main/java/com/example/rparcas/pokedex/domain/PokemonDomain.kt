package com.example.rparcas.pokedex.domain

import com.example.rparcas.pokedex.data.database.entities.PokemonEntity
import com.example.rparcas.pokedex.data.model.PokemonModel
import com.example.rparcas.pokedex.data.model.PokemonStatApi
import com.example.rparcas.pokedex.data.model.PokemonTypes
import com.google.gson.Gson

data class PokemonDomain (
    val id:Int,
    val order: String,
    val name:String,
    val officialArtwork: String?,
    val sprites: List<String>,
    val tipos: List<PokemonTypes>,
    val base_experience: Int,
    val height: Int,
    val weight: Int,
    val stats: List<PokemonStat>,
    var isFav: Boolean
)



fun PokemonModel.toDomain() = PokemonDomain(id = id,order=order,name=name,
    officialArtwork = sprites.otherSprites.officialArtWork?.frontDefault,
    sprites = listOf(sprites.frontDefault,sprites.backDefault),
    tipos = types,
    base_experience = base_experience, height = height, weight = weight,
    stats = stats.map { it.toDomain() },
    isFav = false
    )

fun PokemonEntity.toDomain() = PokemonDomain(id = id,order=order,name=name,
    officialArtwork = officialArtwork,
    sprites = Gson().fromJson(sprites,Array<String>::class.java).toList(),
    tipos = Gson().fromJson(tipos,Array<PokemonTypes>::class.java).toList(),
    base_experience = base_experience, height = height, weight = weight,
    stats = Gson().fromJson(stats,Array<PokemonStat>::class.java).toList(),
    isFav = isFav == 1 // se guarda en 1 o 0
)



data class PokemonStat(
    val value: Int,
    val name: String
)

fun PokemonStatApi.toDomain() = PokemonStat(
    name = pokemonStat.name,
    value = value
)
package com.example.rparcas.pokedex.domain

import com.example.rparcas.pokedex.data.database.entities.PokemonEntity
import com.example.rparcas.pokedex.data.model.PokemonModel
import com.example.rparcas.pokedex.data.model.PokemonTypes
import com.google.gson.Gson

data class PokemonDomain (
    val id:Int,
    val order: String,
    val name:String,
    val officialArtwork: String?,
    val sprites: List<String>,
    val tipos: List<PokemonTypes>
)

fun PokemonModel.toDomain() = PokemonDomain(id = id,order=order,name=name,
    officialArtwork = sprites.otherSprites.officialArtWork?.frontDefault,
    sprites = listOf(sprites.frontDefault,sprites.backDefault),
    tipos = types
    )

fun PokemonEntity.toDomain() = PokemonDomain(id = id,order=order,name=name,
    officialArtwork = officialArtwork,
    sprites = Gson().fromJson(sprites,Array<String>::class.java).toList(),
    tipos = Gson().fromJson(tipos,Array<PokemonTypes>::class.java).toList()
)
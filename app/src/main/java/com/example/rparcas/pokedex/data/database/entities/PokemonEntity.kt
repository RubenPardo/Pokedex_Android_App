package com.example.rparcas.pokedex.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rparcas.pokedex.data.model.PokemonTypes
import com.example.rparcas.pokedex.domain.PokemonDomain
import com.google.gson.Gson

@Entity(tableName = "pokemon_table")
data class PokemonEntity (
    @PrimaryKey() @ColumnInfo(name = "id") val id:Int=0,
    @ColumnInfo(name = "order") val order:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "officialArtwork") val officialArtwork:String?,
    @ColumnInfo(name = "sprites") val sprites:String,
    @ColumnInfo(name = "tipos") val tipos:String,
)



fun PokemonDomain.toDatabase() =
    PokemonEntity(
        id=id,
        order = order,
        name = name,
        officialArtwork = officialArtwork,
        sprites = Gson().toJson(sprites),
        tipos = Gson().toJson(tipos)

    )
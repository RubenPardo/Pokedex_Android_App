package com.example.rparcas.pokedex.di

import android.content.Context
import androidx.room.Room
import com.example.rparcas.pokedex.data.database.PokemonDatabase
import com.example.rparcas.pokedex.data.database.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val POKEMON_DATABASE_NAME = "pokemon_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): PokemonDatabase {
        return   Room.databaseBuilder(context,PokemonDatabase::class.java,POKEMON_DATABASE_NAME).build()
    }


    @Singleton
    @Provides
    fun providePokemonDao(db:PokemonDatabase): PokemonDao {
        return db.getPokemonDao()
    }

}
package com.example.marvelstudios.data

import android.util.Log
import com.example.marvelstudios.api.MarvelApi
import com.example.marvelstudios.data.CharacterDao
import com.example.marvelstudios.data.toUIModel
import com.example.marvelstudios.data.CharacterUI
import com.example.marvelstudios.data.toCharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepository
constructor(
    private val apiService: MarvelApi,
    private val characterDao: CharacterDao
) {
    suspend fun refreshCharacters(apiKey: String, hash: String, ts: Long) {
        try {
            Log.d("CharacterRepository", "Refreshing characters...")
            val response = apiService.getCharacters(apiKey, hash, ts)
            if (response.isSuccessful) {
                response.body()?.data?.results?.let { characterList ->
                    characterDao.insertCharacters(characterList.map { it.toCharacterEntity() })
                }
            } else {
                Log.e("CharacterRepository", "Failed to refresh characters: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Error refreshing characters: ${e.message}")
        }
    }

    fun getAllCharacters(): Flow<List<CharacterUI>> {
        return characterDao.getAllCharacters()
            .map { characterEntities ->
                Log.d("CharacterRepository", "Получено ${characterEntities.size} персонажей из базы данных")
                characterEntities.map { it.toUIModel() }
            }
    }
}
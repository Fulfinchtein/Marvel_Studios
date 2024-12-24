package com.example.marvelstudios.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest

class CharacterViewModel : ViewModel() {
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters = _characters.asStateFlow()

    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val selectedCharacter = _selectedCharacter.asStateFlow()

    val errorMessage = MutableStateFlow<String?>(null)
    val isLoading = MutableStateFlow(false)  // Для отслеживания загрузки

    private val apiKey = "378b183c534f3cbbbc0e9afb1ea09414"
    private val privateKey = "e6094c6085eaf20b7e32b7b07baab4e26f19ff4f"


    fun generateHash(privateKey: String, apiKey: String, ts: Long): String {
        val md5 = MessageDigest.getInstance("MD5")
        val input = "$ts$privateKey$apiKey"
        val digest = md5.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun getCharacters(apiKey: String, privateKey: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val ts = System.currentTimeMillis()
                val hash = generateHash(
                    this@CharacterViewModel.privateKey,
                    this@CharacterViewModel.apiKey, ts)
                val response = RetrofitInstance.apiService.getCharacters(
                    this@CharacterViewModel.apiKey,
                    hash,
                    ts
                )

                if (response.isSuccessful) {
                    val marvelResponse = response.body()
                    if (marvelResponse != null) {
                        _characters.value = marvelResponse.data.results
                    } else {
                        errorMessage.value = "Не удалось получить данные."
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Неизвестная ошибка"
                    errorMessage.value = "Ошибка загрузки персонажей: $errorBody"
                }
            } catch (e: Exception) {
                errorMessage.value = "Ошибка: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun getCharacterDetails(characterId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            _selectedCharacter.value = null

            if (characterId <= 0) {
                _selectedCharacter.value = Character(
                    id = -1,
                    name = "Unknown Hero",
                    description = "Invalid character ID.",
                    thumbnail = Thumbnail(
                        path = "https://via.placeholder.com/300",
                        extension = "png"
                    )
                )
                isLoading.value = false
                return@launch
            }

            try {
                val ts = System.currentTimeMillis()
                val hash = generateHash(privateKey, apiKey, ts)
                val response = RetrofitInstance.apiService.getCharacter(
                    characterId,
                    apiKey,
                    hash,
                    ts
                )

                if (response.isSuccessful) {
                    val marvelResponse = response.body()
                    if (marvelResponse != null && marvelResponse.data.results.isNotEmpty()) {
                        _selectedCharacter.value = marvelResponse.data.results.first()
                    } else {
                        _selectedCharacter.value = Character(
                            id = -1,
                            name = "Unknown Hero",
                            description = "Character not found.",
                            thumbnail = Thumbnail(
                                path = "https://via.placeholder.com/300",
                                extension = "png"
                            )
                        )
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    errorMessage.value = "Error loading hero: $errorBody"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}

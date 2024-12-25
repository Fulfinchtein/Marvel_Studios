package com.example.marvelstudios.api

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marvelstudios.data.CharacterUI
import com.example.marvelstudios.data.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterViewModelFactory(
    private val characterRepository: CharacterRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(characterRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

class CharacterViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    companion object {
        private const val API_KEY = "b9df47947ab951d8c0a86014efe43711"
        private const val PRIVATE_KEY = "9c213ff9bd717d9da92e0ce3798ce9903baf19ba"
    }

    private val _characters = MutableStateFlow<List<CharacterUI>>(emptyList())
    val characters: StateFlow<List<CharacterUI>> = _characters

    private val _characterImageUrl = MutableStateFlow<String?>(null)
    val characterImageUrl: StateFlow<String?> = _characterImageUrl

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getCharacters(isNetworkAvailable: Boolean) {
        if (!isNetworkAvailable) {
            getCharactersFromDatabase()
            _errorMessage.value = "Нет подключения к сети"
            return
        }

        val ts = System.currentTimeMillis()
        val hash = generateHash(PRIVATE_KEY, API_KEY, ts)

        viewModelScope.launch {
            _isLoading.value = true
            try {
                characterRepository.refreshCharacters(API_KEY, hash, ts)
                getCharactersFromDatabase()
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка: ${e.message}"
                Log.e("CharacterViewModel", "Ошибка получения списка персонажей: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getCharactersFromDatabase() {
        viewModelScope.launch {
            characterRepository.getAllCharacters().collect { characterList ->
                _characters.value = characterList
            }
        }
    }

    private fun generateHash(privateKey: String, publicKey: String, ts: Long): String {
        return (ts.toString() + privateKey + publicKey).md5()
    }

    private fun String.md5(): String {
        return java.security.MessageDigest.getInstance("MD5")
            .digest(toByteArray())
            .joinToString("") { "%02x".format(it) }
    }
}

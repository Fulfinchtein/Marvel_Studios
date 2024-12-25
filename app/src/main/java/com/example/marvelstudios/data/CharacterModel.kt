package com.example.marvelstudios.data

import com.example.marvelstudios.data.CharacterEntity
import com.example.marvelstudios.api.Character
import com.example.marvelstudios.api.Thumbnail

data class CharacterUI(
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String,
    val thumbnail: Thumbnail? = null
)
fun Character.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = "${this.thumbnail.path}.${this.thumbnail.extension}".replace("http://", "https://"),
    )
}
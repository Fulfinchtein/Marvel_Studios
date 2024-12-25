package com.example.marvelstudios.data

data class ThumbnailDTO(
    val path: String,
    val extension: String
)

data class CharacterDTO(
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String,
    val thumbnail: ThumbnailDTO
)

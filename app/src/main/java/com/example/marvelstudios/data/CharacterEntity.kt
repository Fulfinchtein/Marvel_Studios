package com.example.marvelstudios.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvelstudios.data.CharacterUI

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String
)

fun CharacterEntity.toUIModel(): CharacterUI {
    return CharacterUI(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl
    )
}

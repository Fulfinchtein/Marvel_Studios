package com.example.marvelstudios.data

data class Hero(
    val id: Int,
    val name: String,
    val heroImageUrl: String
) {
    companion object {
        fun getHeroes(): List<Hero> {
            return listOf(
                Hero(
                    id = 1,
                    name = "Deadpool",
                    heroImageUrl = "https://iili.io/JMnAfIV.png"
                ),
                Hero(
                    id = 2,
                    name = "Iron Man",
                    heroImageUrl = "https://iili.io/JMnuDI2.png"
                ),
                Hero(
                    id = 3,
                    name = "Spider-Man",
                    heroImageUrl = "https://iili.io/JMnuyB9.png"
                )
            )
        }
    }
}
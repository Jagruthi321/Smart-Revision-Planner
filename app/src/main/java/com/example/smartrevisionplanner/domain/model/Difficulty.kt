package com.example.smartrevisionplanner.domain.model

enum class Difficulty {
    Easy, Medium, Hard
}

fun Difficulty.weight(): Int = when (this) {
    Difficulty.Easy -> 1
    Difficulty.Medium -> 2
    Difficulty.Hard -> 3
}
package com.example.smartrevisionplanner.domain.model

enum class Difficulty {
    Easy,
    Medium,
    Hard;

    val weight: Int
        get() = when (this) {
            Easy -> 1
            Medium -> 2
            Hard -> 3
        }
}

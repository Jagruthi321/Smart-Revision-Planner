package com.example.smartrevisionplanner.domain.model

enum class Importance {
    High, Medium, Low;

    companion object {
        fun fromString(value: String): Importance = when (value.lowercase()) {
            "high" -> High
            "medium" -> Medium
            "low" -> Low
            else -> Medium
        }
    }
}

fun Importance.weight(): Int = when (this) {
    Importance.High -> 3
    Importance.Medium -> 2
    Importance.Low -> 1
}
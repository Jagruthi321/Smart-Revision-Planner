package com.example.smartrevisionplanner.domain.model

enum class Importance {
    High,
    Medium,
    Low;

    val weight: Int
        get() = when (this) {
            High -> 3
            Medium -> 2
            Low -> 1
        }

    companion object {
        fun fromString(value: String): Importance = when (value.lowercase()) {
            "high" -> High
            "medium" -> Medium
            "low" -> Low
            else -> Medium
        }
    }
}

package com.example.smartrevisionplanner.domain.model

data class ChapterMetadata(
    val id: String,
    val name: String,
    val subject: String,
    val topicKeyword: String,
    val importance: Importance
)

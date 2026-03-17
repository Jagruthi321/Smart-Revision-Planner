package com.example.smartrevisionplanner.domain.model

/**
 * Chapter selected by user with assigned difficulty.
 */
data class ChapterSelection(
    val metadata: ChapterMetadata,
    val difficulty: Difficulty
) {
    val id: String get() = metadata.id
    val name: String get() = metadata.name
    val subject: String get() = metadata.subject
    val topicKeyword: String get() = metadata.topicKeyword
    val importance: Importance get() = metadata.importance
    val difficultyWeight: Int get() = difficulty.weight()
    val estimatedHours: Float get() = difficulty.weight().toFloat()
}

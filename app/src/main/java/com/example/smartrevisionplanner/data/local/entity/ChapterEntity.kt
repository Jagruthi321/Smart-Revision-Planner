package com.example.smartrevisionplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smartrevisionplanner.domain.model.ChapterStatus
import com.example.smartrevisionplanner.domain.model.Difficulty
import com.example.smartrevisionplanner.domain.model.Importance

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val subject: String,
    val difficulty: Difficulty,
    val difficultyWeight: Int,
    val estimatedHours: Float,
    val assignedDateMillis: Long,
    val isScheduled: Boolean,
    val status: ChapterStatus,
    val completionTimestamp: Long,
    val topicKeyword: String,
    val notes: String?,
    val priority: Importance,
    val revisionCount: Int
)

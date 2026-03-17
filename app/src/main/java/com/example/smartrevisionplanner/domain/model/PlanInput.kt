package com.example.smartrevisionplanner.domain.model

/**
 * User input for plan generation.
 */
data class PlanInput(
    val examDateMillis: Long,
    val studyHoursPerDay: Float?,
    val selectedSubjects: List<Subject>,
    val selectedChapters: List<ChapterSelection>
)

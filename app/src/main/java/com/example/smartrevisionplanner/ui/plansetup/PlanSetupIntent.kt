package com.example.smartrevisionplanner.ui.plansetup

import com.example.smartrevisionplanner.domain.model.ChapterMetadata
import com.example.smartrevisionplanner.domain.model.ChapterSelection
import com.example.smartrevisionplanner.domain.model.Difficulty
import com.example.smartrevisionplanner.domain.model.Subject
import com.example.smartrevisionplanner.ui.mvi.MviIntent

sealed interface PlanSetupIntent : MviIntent {
    data object LoadSubjects : PlanSetupIntent
    data object LoadChaptersForSelectedSubjects : PlanSetupIntent
    data class SetExamDate(val dateMillis: Long) : PlanSetupIntent
    data class SetStudyHours(val hours: Float?) : PlanSetupIntent
    data class LoadSubjects(val subjects: List<Subject>) : PlanSetupIntent
    data class ToggleSubject(val subject: Subject) : PlanSetupIntent
    data class LoadChapters(val chapters: List<ChapterMetadata>) : PlanSetupIntent
    data class ToggleChapter(val metadata: ChapterMetadata) : PlanSetupIntent
    data class SetChapterDifficulty(val metadata: ChapterMetadata, val difficulty: Difficulty) : PlanSetupIntent
    data object GeneratePlan : PlanSetupIntent
    data class PlanSaved(val success: Boolean) : PlanSetupIntent
    data class SetError(val message: String?) : PlanSetupIntent
}

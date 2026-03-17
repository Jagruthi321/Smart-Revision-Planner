package com.example.smartrevisionplanner.ui.plansetup

import com.example.smartrevisionplanner.domain.model.ChapterMetadata
import com.example.smartrevisionplanner.domain.model.ChapterSelection
import com.example.smartrevisionplanner.domain.model.Difficulty
import com.example.smartrevisionplanner.domain.model.Subject
import com.example.smartrevisionplanner.ui.mvi.MviState

data class PlanSetupState(
    val examDateMillis: Long? = null,
    val studyHoursPerDay: Float? = null,
    val allSubjects: List<Subject> = emptyList(),
    val selectedSubjects: Set<Subject> = emptySet(),
    val allChapters: List<ChapterMetadata> = emptyList(),
    val selectedChapters: Map<ChapterMetadata, Difficulty> = emptyMap(),
    val isGenerating: Boolean = false,
    val planSaved: Boolean = false,
    val error: String? = null
) : MviState {

    val selectedChaptersList: List<ChapterSelection>
        get() = selectedChapters.map { (meta, diff) -> ChapterSelection(meta, diff) }

    val hasExamDate: Boolean get() = examDateMillis != null
    val hasSubjects: Boolean get() = selectedSubjects.isNotEmpty()
    val hasChapters: Boolean get() = selectedChapters.isNotEmpty()
}

package com.example.smartrevisionplanner.ui.plansetup

import com.example.smartrevisionplanner.data.repository.ChapterRepository
import com.example.smartrevisionplanner.data.repository.SyllabusRepository
import com.example.smartrevisionplanner.domain.model.ChapterMetadata
import com.example.smartrevisionplanner.domain.model.Difficulty
import com.example.smartrevisionplanner.domain.model.PlanInput
import com.example.smartrevisionplanner.domain.model.Subject
import com.example.smartrevisionplanner.domain.usecase.GenerateAndSavePlanUseCase
import com.example.smartrevisionplanner.ui.mvi.MviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlanSetupViewModel(
    private val syllabusRepository: SyllabusRepository,
    private val generateAndSavePlanUseCase: GenerateAndSavePlanUseCase,
    private val chapterRepository: ChapterRepository,
    private val onPlanSaved: () -> Unit
) : MviViewModel<PlanSetupIntent, PlanSetupState>(PlanSetupState()) {

    init {
        sendIntent(PlanSetupIntent.LoadSubjects)
    }

    override fun handleIntent(intent: PlanSetupIntent) {
        when (intent) {
            PlanSetupIntent.LoadSubjects -> viewModelScope.launch {
                val subjects = syllabusRepository.loadSubjects()
                updateState { it.copy(allSubjects = subjects) }
            }
            PlanSetupIntent.LoadChaptersForSelectedSubjects -> viewModelScope.launch {
                val current = state.value
                val chapters = current.selectedSubjects.flatMap { subject ->
                    syllabusRepository.loadChaptersForSubject(subject.id)
                }
                updateState { it.copy(allChapters = chapters) }
            }
            is PlanSetupIntent.SetExamDate -> updateState {
                it.copy(examDateMillis = intent.dateMillis, error = null)
            }
            is PlanSetupIntent.SetStudyHours -> updateState {
                it.copy(studyHoursPerDay = intent.hours)
            }
            is PlanSetupIntent.LoadSubjects -> updateState {
                it.copy(allSubjects = intent.subjects)
            }
            is PlanSetupIntent.ToggleSubject -> updateState {
                val newSelected = if (intent.subject in it.selectedSubjects) {
                    it.selectedSubjects - intent.subject
                } else {
                    it.selectedSubjects + intent.subject
                }
                it.copy(
                    selectedSubjects = newSelected,
                    selectedChapters = it.selectedChapters.filter { (meta, _) -> meta.subject in newSelected.map { s -> s.id } }
                )
            }
            is PlanSetupIntent.LoadChapters -> updateState {
                it.copy(allChapters = intent.chapters)
            }
            is PlanSetupIntent.ToggleChapter -> updateState {
                val newMap = if (intent.metadata in it.selectedChapters) {
                    it.selectedChapters - intent.metadata
                } else {
                    it.selectedChapters + (intent.metadata to Difficulty.Medium)
                }
                it.copy(selectedChapters = newMap)
            }
            is PlanSetupIntent.SetChapterDifficulty -> updateState {
                if (intent.metadata in it.selectedChapters) {
                    it.copy(selectedChapters = it.selectedChapters + (intent.metadata to intent.difficulty))
                } else {
                    it
                }
            }
            PlanSetupIntent.GeneratePlan -> generateAndSavePlan()
            is PlanSetupIntent.PlanSaved -> updateState {
                it.copy(isGenerating = false, planSaved = intent.success, error = if (intent.success) null else it.error)
            }
            is PlanSetupIntent.SetError -> updateState {
                it.copy(error = intent.message)
            }
        }
    }

    private fun generateAndSavePlan() {
        val currentState = state.value
        val examDate = currentState.examDateMillis
        if (examDate == null) {
            updateState { it.copy(error = "Please select exam date") }
            return
        }
        if (currentState.selectedChapters.isEmpty()) {
            updateState { it.copy(error = "Please select at least one chapter") }
            return
        }

        updateState { it.copy(isGenerating = true, error = null) }

        viewModelScope.launch {
            try {
                val entities = withContext(Dispatchers.Default) {
                    generateAndSavePlanUseCase.generateSchedule(
                        PlanInput(
                            examDateMillis = examDate,
                            studyHoursPerDay = currentState.studyHoursPerDay,
                            selectedSubjects = currentState.selectedSubjects.toList(),
                            selectedChapters = currentState.selectedChaptersList
                        )
                    )
                }
                withContext(Dispatchers.IO) {
                    chapterRepository.replacePlan(entities)
                }
                updateState { it.copy(isGenerating = false, planSaved = true) }
                onPlanSaved()
            } catch (e: Exception) {
                updateState {
                    it.copy(isGenerating = false, error = e.message ?: "Failed to generate plan")
                }
            }
        }
    }
}

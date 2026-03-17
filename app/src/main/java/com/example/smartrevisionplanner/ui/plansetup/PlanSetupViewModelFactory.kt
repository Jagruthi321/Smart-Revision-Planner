package com.example.smartrevisionplanner.ui.plansetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartrevisionplanner.data.repository.ChapterRepository
import com.example.smartrevisionplanner.data.repository.SyllabusRepository
import com.example.smartrevisionplanner.domain.engine.PlannerEngine
import com.example.smartrevisionplanner.domain.usecase.GenerateAndSavePlanUseCase

class PlanSetupViewModelFactory(
    private val syllabusRepository: SyllabusRepository,
    private val chapterRepository: ChapterRepository,
    private val onPlanSaved: () -> Unit
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val useCase = GenerateAndSavePlanUseCase(PlannerEngine())
        return PlanSetupViewModel(
            syllabusRepository = syllabusRepository,
            generateAndSavePlanUseCase = useCase,
            chapterRepository = chapterRepository,
            onPlanSaved = onPlanSaved
        ) as T
    }
}

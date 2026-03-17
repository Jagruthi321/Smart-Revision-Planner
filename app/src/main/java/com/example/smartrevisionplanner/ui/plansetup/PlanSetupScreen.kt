package com.example.smartrevisionplanner.ui.plansetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

const val ROUTE_EXAM_DATE = "exam_date"
const val ROUTE_SUBJECTS = "subjects"
const val ROUTE_CHAPTERS = "chapters"
const val ROUTE_DIFFICULTY = "difficulty"

@Composable
fun PlanSetupScreen(
    viewModel: PlanSetupViewModel,
    onPlanSaved: () -> Unit,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(state.planSaved) {
        if (state.planSaved) {
            onPlanSaved()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }
        NavHost(
            navController = navController,
            startDestination = ROUTE_EXAM_DATE,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(ROUTE_EXAM_DATE) {
                ExamDateScreen(
                    examDateMillis = state.examDateMillis,
                    studyHoursPerDay = state.studyHoursPerDay,
                    onExamDateChange = { viewModel.sendIntent(PlanSetupIntent.SetExamDate(it)) },
                    onStudyHoursChange = { viewModel.sendIntent(PlanSetupIntent.SetStudyHours(it)) },
                    onNext = { navController.navigate(ROUTE_SUBJECTS) }
                )
            }
            composable(ROUTE_SUBJECTS) {
                SubjectSelectionScreen(
                    subjects = state.allSubjects,
                    selectedSubjects = state.selectedSubjects,
                    onToggleSubject = { viewModel.sendIntent(PlanSetupIntent.ToggleSubject(it)) },
                    onNext = { navController.navigate(ROUTE_CHAPTERS) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(ROUTE_CHAPTERS) {
                LaunchedEffect(Unit) {
                    viewModel.sendIntent(PlanSetupIntent.LoadChaptersForSelectedSubjects)
                }
                ChapterSelectionScreen(
                    chapters = state.allChapters,
                    selectedChapters = state.selectedChapters.keys,
                    onToggleChapter = { viewModel.sendIntent(PlanSetupIntent.ToggleChapter(it)) },
                    onNext = { navController.navigate(ROUTE_DIFFICULTY) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(ROUTE_DIFFICULTY) {
                DifficultyAssignmentScreen(
                    selectedChapters = state.selectedChaptersList,
                    chapterDifficulties = state.selectedChapters,
                    onDifficultyChange = { meta, diff ->
                        viewModel.sendIntent(PlanSetupIntent.SetChapterDifficulty(meta, diff))
                    },
                    onGenerate = { viewModel.sendIntent(PlanSetupIntent.GeneratePlan) },
                    onBack = { navController.popBackStack() },
                    isGenerating = state.isGenerating
                )
            }
        }
    }}

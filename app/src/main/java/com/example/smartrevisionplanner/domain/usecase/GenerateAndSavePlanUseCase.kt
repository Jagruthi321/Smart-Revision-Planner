package com.example.smartrevisionplanner.domain.usecase

import com.example.smartrevisionplanner.data.local.entity.ChapterEntity
import com.example.smartrevisionplanner.domain.engine.PlannerEngine
import com.example.smartrevisionplanner.domain.engine.ScheduledChapter
import com.example.smartrevisionplanner.domain.model.ChapterStatus
import com.example.smartrevisionplanner.domain.model.Importance
import com.example.smartrevisionplanner.domain.model.PlanInput

/**
 * Generates schedule via PlannerEngine and maps to ChapterEntity for Room persistence.
 */
class GenerateAndSavePlanUseCase(
    private val plannerEngine: PlannerEngine
) {

    fun generateSchedule(input: PlanInput): List<ChapterEntity> {
        val schedule = plannerEngine.generateSchedule(input)
        return schedule.flatMap { (dateMillis, chapters) ->
            chapters.map { sc ->
                ChapterEntity(
                    id = sc.id,
                    name = sc.name,
                    subject = sc.subject,
                    difficulty = sc.difficulty,
                    difficultyWeight = sc.difficultyWeight,
                    estimatedHours = sc.estimatedHours,
                    assignedDateMillis = dateMillis,
                    isScheduled = true,
                    status = ChapterStatus.Pending,
                    completionTimestamp = 0L,
                    topicKeyword = sc.topicKeyword,
                    notes = null,
                    priority = sc.importance,
                    revisionCount = 0
                )
            }
        }
    }
}

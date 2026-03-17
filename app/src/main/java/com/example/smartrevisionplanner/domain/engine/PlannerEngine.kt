package com.example.smartrevisionplanner.domain.engine

import com.example.smartrevisionplanner.domain.model.ChapterSelection
import com.example.smartrevisionplanner.domain.model.Difficulty
import com.example.smartrevisionplanner.domain.model.Importance
import com.example.smartrevisionplanner.domain.model.PlanInput
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * Distributes chapters across available days.
 * Spec: workload_per_day = total_weight / total_days, balanced subject mix.
 */
class PlannerEngine {

    private val revisionBufferDays = 3

    /**
     * Generates day-wise schedule.
     * @return List of (dateMillis, chapters for that day)
     */
    fun generateSchedule(input: PlanInput): List<Pair<Long, List<ScheduledChapter>>> {
        val today = startOfDay(System.currentTimeMillis())
        val examDate = startOfDay(input.examDateMillis)

        if (examDate <= today) return emptyList()
        if (input.selectedChapters.isEmpty()) return emptyList()

        val totalDays = daysBetween(today, examDate).toInt()
        val studyDays = (totalDays - revisionBufferDays).coerceAtLeast(1)

        val totalWeight = input.selectedChapters.sumOf { it.difficultyWeight }
        val workloadPerDay = totalWeight.toDouble() / studyDays

        val sortedChapters = input.selectedChapters
            .sortedWith(
                compareBy<ChapterSelection> { it.subject }
                    .thenByDescending { it.difficultyWeight }
            )
            .toMutableList()

        val result = mutableListOf<Pair<Long, MutableList<ScheduledChapter>>>()
        var currentDate = today
        var dayIndex = 0

        while (sortedChapters.isNotEmpty() && dayIndex < studyDays) {
            val dayChapters = mutableListOf<ScheduledChapter>()
            var dayWeight = 0.0

            val iterator = sortedChapters.iterator()
            while (iterator.hasNext()) {
                val chapter = iterator.next()
                if (dayWeight + chapter.difficultyWeight <= workloadPerDay + 1.0 || dayChapters.isEmpty()) {
                    dayChapters.add(
                        ScheduledChapter(
                            id = chapter.id,
                            name = chapter.name,
                            subject = chapter.subject,
                            difficulty = chapter.difficulty,
                            difficultyWeight = chapter.difficultyWeight,
                            estimatedHours = chapter.estimatedHours,
                            topicKeyword = chapter.topicKeyword,
                            importance = chapter.importance
                        )
                    )
                    dayWeight += chapter.difficultyWeight
                    iterator.remove()
                }
            }

            if (dayChapters.isNotEmpty()) {
                result.add(currentDate to dayChapters)
                dayIndex++
            }
            currentDate = addDays(currentDate, 1)
        }

        sortedChapters.forEach { c ->
            result.add(currentDate to mutableListOf(
                ScheduledChapter(
                    id = c.id,
                    name = c.name,
                    subject = c.subject,
                    difficulty = c.difficulty,
                    difficultyWeight = c.difficultyWeight,
                    estimatedHours = c.estimatedHours,
                    topicKeyword = c.topicKeyword,
                    importance = c.importance
                )
            ))
            currentDate = addDays(currentDate, 1)
        }

        return result.map { (date, chapters) -> date to chapters.toList() }
    }

    private fun startOfDay(millis: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = millis
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    private fun daysBetween(start: Long, end: Long): Long {
        return TimeUnit.MILLISECONDS.toDays(end - start)
    }

    private fun addDays(millis: Long, days: Int): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = millis
        cal.add(Calendar.DAY_OF_YEAR, days)
        return cal.timeInMillis
    }
}

/**
 * Chapter with scheduling info for a specific day.
 */
data class ScheduledChapter(
    val id: String,
    val name: String,
    val subject: String,
    val difficulty: Difficulty,
    val difficultyWeight: Int,
    val estimatedHours: Float,
    val topicKeyword: String,
    val importance: Importance
)

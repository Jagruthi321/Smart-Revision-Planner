package com.example.smartrevisionplanner.data.local

import android.content.Context
import com.example.smartrevisionplanner.domain.model.ChapterMetadata
import com.example.smartrevisionplanner.domain.model.Importance
import com.example.smartrevisionplanner.domain.model.Subject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

/**
 * Parses static JSON assets for subjects, chapters, and importance.
 * Provides offline-first syllabus data.
 */
class SyllabusParser(private val context: Context) {

    private val gson = Gson()

    suspend fun loadSubjects(): List<Subject> = withContext(Dispatchers.IO) {
        val json = context.assets.open("subjects.json").reader(Charsets.UTF_8).readText()
        val type = object : TypeToken<List<SubjectDto>>() {}.type
        val dtos: List<SubjectDto> = gson.fromJson(json, type)
        dtos.map { Subject(id = it.id, name = it.name) }
    }

    suspend fun loadChaptersWithImportance(): List<ChapterMetadata> = withContext(Dispatchers.IO) {
        val chaptersJson = context.assets.open("chapters.json").reader(Charsets.UTF_8).readText()
        val importanceJson = context.assets.open("importance.json").reader(Charsets.UTF_8).readText()

        val chaptersMap = gson.fromJson(chaptersJson, ChaptersDto::class.java)
        val importanceMap = gson.fromJson(importanceJson, ImportanceDto::class.java)

        val result = mutableListOf<ChapterMetadata>()
        for ((subject, chapters) in chaptersMap) {
            val subjectImportance = importanceMap[subject] ?: emptyMap()
            for (chapter in chapters) {
                val importanceStr = subjectImportance[chapter.id] ?: "Medium"
                result.add(
                    ChapterMetadata(
                        id = chapter.id,
                        name = chapter.name,
                        subject = subject,
                        topicKeyword = chapter.topicKeyword,
                        importance = Importance.fromString(importanceStr)
                    )
                )
            }
        }
        result
    }

    suspend fun loadChaptersForSubject(subjectId: String): List<ChapterMetadata> {
        return loadChaptersWithImportance().filter { it.subject == subjectId }
    }

    private data class SubjectDto(val id: String, val name: String)

    private data class ChapterDto(val id: String, val name: String, val topicKeyword: String)

    private typealias ChaptersDto = Map<String, List<ChapterDto>>
    private typealias ImportanceDto = Map<String, Map<String, String>>
}

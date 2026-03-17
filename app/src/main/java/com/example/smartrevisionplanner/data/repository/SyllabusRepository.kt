package com.example.smartrevisionplanner.data.repository

import com.example.smartrevisionplanner.data.local.SyllabusParser
import com.example.smartrevisionplanner.domain.model.ChapterMetadata
import com.example.smartrevisionplanner.domain.model.Subject

class SyllabusRepository(private val syllabusParser: SyllabusParser) {

    suspend fun loadSubjects(): List<Subject> = syllabusParser.loadSubjects()

    suspend fun loadChaptersWithImportance(): List<ChapterMetadata> =
        syllabusParser.loadChaptersWithImportance()

    suspend fun loadChaptersForSubject(subjectId: String): List<ChapterMetadata> =
        syllabusParser.loadChaptersForSubject(subjectId)
}

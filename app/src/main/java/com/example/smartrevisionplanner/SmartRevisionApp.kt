package com.example.smartrevisionplanner

import android.app.Application
import com.example.smartrevisionplanner.data.local.AppDatabase
import com.example.smartrevisionplanner.data.local.SyllabusParser
import com.example.smartrevisionplanner.data.repository.ChapterRepository
import com.example.smartrevisionplanner.data.repository.SyllabusRepository

class SmartRevisionApp : Application() {

    val appDatabase: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val syllabusParser: SyllabusParser by lazy { SyllabusParser(this) }
    val syllabusRepository: SyllabusRepository by lazy { SyllabusRepository(syllabusParser) }
    val chapterRepository: ChapterRepository by lazy { ChapterRepository(appDatabase.chapterDao()) }
}

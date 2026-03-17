package com.example.smartrevisionplanner.data.repository

import com.example.smartrevisionplanner.data.local.dao.ChapterDao
import com.example.smartrevisionplanner.data.local.entity.ChapterEntity
import com.example.smartrevisionplanner.domain.model.ChapterStatus
import kotlinx.coroutines.flow.Flow

class ChapterRepository(private val chapterDao: ChapterDao) {

    fun getAllChapters(): Flow<List<ChapterEntity>> = chapterDao.getAllChapters()

    fun getChaptersForDate(dateMillis: Long): Flow<List<ChapterEntity>> =
        chapterDao.getChaptersForDate(dateMillis)

    fun getChaptersInDateRange(startMillis: Long, endMillis: Long): Flow<List<ChapterEntity>> =
        chapterDao.getChaptersInDateRange(startMillis, endMillis)

    suspend fun insertAll(chapters: List<ChapterEntity>) = chapterDao.insertAll(chapters)

    suspend fun replacePlan(chapters: List<ChapterEntity>) {
        chapterDao.deleteAll()
        chapterDao.insertAll(chapters)
    }

    suspend fun insert(chapter: ChapterEntity) = chapterDao.insert(chapter)

    suspend fun update(chapter: ChapterEntity) = chapterDao.update(chapter)

    suspend fun updateStatus(chapterId: String, status: ChapterStatus, timestamp: Long = System.currentTimeMillis()) =
        chapterDao.updateStatus(chapterId, status.name, timestamp)

    suspend fun getCompletedCount(): Int = chapterDao.getCompletedCount()

    suspend fun getTotalCount(): Int = chapterDao.getTotalCount()
}

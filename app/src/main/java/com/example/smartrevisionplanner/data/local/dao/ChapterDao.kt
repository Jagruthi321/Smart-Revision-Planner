package com.example.smartrevisionplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.smartrevisionplanner.data.local.entity.ChapterEntity
import com.example.smartrevisionplanner.domain.model.ChapterStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chapters: List<ChapterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chapter: ChapterEntity)

    @Update
    suspend fun update(chapter: ChapterEntity)

    @Query("SELECT * FROM chapters WHERE id = :chapterId")
    suspend fun getById(chapterId: String): ChapterEntity?

    @Query("SELECT * FROM chapters ORDER BY assignedDateMillis, subject")
    fun getAllChapters(): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE assignedDateMillis = :dateMillis ORDER BY subject")
    fun getChaptersForDate(dateMillis: Long): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE assignedDateMillis BETWEEN :startMillis AND :endMillis ORDER BY assignedDateMillis, subject")
    fun getChaptersInDateRange(startMillis: Long, endMillis: Long): Flow<List<ChapterEntity>>

    @Query("SELECT * FROM chapters WHERE status = :status")
    fun getChaptersByStatus(status: ChapterStatus): Flow<List<ChapterEntity>>

    @Query("UPDATE chapters SET status = :status, completionTimestamp = :timestamp WHERE id = :chapterId")
    suspend fun updateStatus(chapterId: String, status: ChapterStatus, timestamp: Long = System.currentTimeMillis())

    @Query("DELETE FROM chapters")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM chapters WHERE status = 'Completed'")
    suspend fun getCompletedCount(): Int

    @Query("SELECT COUNT(*) FROM chapters")
    suspend fun getTotalCount(): Int
}

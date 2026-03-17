package com.example.smartrevisionplanner.data.local

import androidx.room.TypeConverter
import com.example.smartrevisionplanner.domain.model.ChapterStatus
import com.example.smartrevisionplanner.domain.model.Difficulty
import com.example.smartrevisionplanner.domain.model.Importance

class Converters {

    @TypeConverter
    fun fromDifficulty(value: Difficulty): String = value.name

    @TypeConverter
    fun toDifficulty(value: String): Difficulty = Difficulty.valueOf(value)

    @TypeConverter
    fun fromChapterStatus(value: ChapterStatus): String = value.name

    @TypeConverter
    fun toChapterStatus(value: String): ChapterStatus = ChapterStatus.valueOf(value)

    @TypeConverter
    fun fromImportance(value: Importance): String = value.name

    @TypeConverter
    fun toImportance(value: String): Importance = Importance.valueOf(value)
}

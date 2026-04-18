package com.example.errandcue.data.db.entity
import androidx.room.Entity; import androidx.room.PrimaryKey
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val notes: String? = null,
    val voiceText: String? = null,
    val triggerType: String = "ARRIVE",
    val travelMode: String = "ANY",
    val status: String = "ACTIVE",
    val createdAt: Long,
    val updatedAt: Long
)
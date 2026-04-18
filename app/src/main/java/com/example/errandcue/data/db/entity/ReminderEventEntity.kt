package com.example.errandcue.data.db.entity
import androidx.room.Entity; import androidx.room.PrimaryKey
@Entity(tableName = "reminder_events")
data class ReminderEventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val taskId: Long,
    val placeId: Long,
    val triggerSource: String,
    val decision: String,
    val spoken: Boolean,
    val userAction: String?,
    val eventTime: Long
)
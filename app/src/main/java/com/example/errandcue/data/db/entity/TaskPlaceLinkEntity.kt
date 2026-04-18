package com.example.errandcue.data.db.entity
import androidx.room.Entity
@Entity(tableName = "task_place_links", primaryKeys = ["taskId", "placeId"])
data class TaskPlaceLinkEntity(val taskId: Long, val placeId: Long)
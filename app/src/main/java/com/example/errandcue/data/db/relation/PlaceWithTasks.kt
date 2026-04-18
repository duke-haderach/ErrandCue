package com.example.errandcue.data.db.relation
import androidx.room.*
import com.example.errandcue.data.db.entity.*
data class PlaceWithTasks(
    @Embedded val place: PlaceEntity,
    @Relation(parentColumn="id", entityColumn="id", associateBy=Junction(value=TaskPlaceLinkEntity::class, parentColumn="placeId", entityColumn="taskId"))
    val tasks: List<TaskEntity>
)
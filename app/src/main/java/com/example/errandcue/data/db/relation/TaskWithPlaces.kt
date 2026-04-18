package com.example.errandcue.data.db.relation
import androidx.room.*
import com.example.errandcue.data.db.entity.*
data class TaskWithPlaces(
    @Embedded val task: TaskEntity,
    @Relation(parentColumn="id", entityColumn="id", associateBy=Junction(value=TaskPlaceLinkEntity::class, parentColumn="taskId", entityColumn="placeId"))
    val places: List<PlaceEntity>
)
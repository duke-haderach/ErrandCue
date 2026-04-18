package com.example.errandcue.data.db.entity
import androidx.room.Entity; import androidx.room.PrimaryKey
@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radiusMeters: Float = 150f,
    val createdAt: Long,
    val updatedAt: Long
)
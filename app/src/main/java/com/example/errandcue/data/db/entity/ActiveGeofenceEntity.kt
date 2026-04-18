package com.example.errandcue.data.db.entity
import androidx.room.Entity; import androidx.room.PrimaryKey
@Entity(tableName = "active_geofences")
data class ActiveGeofenceEntity(@PrimaryKey val placeId: Long, val requestId: String, val armedAt: Long)
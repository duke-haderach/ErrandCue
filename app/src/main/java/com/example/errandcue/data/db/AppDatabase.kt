package com.example.errandcue.data.db
import androidx.room.*
import com.example.errandcue.data.db.dao.*
import com.example.errandcue.data.db.entity.*
@Database(entities=[TaskEntity::class,PlaceEntity::class,TaskPlaceLinkEntity::class,ActiveGeofenceEntity::class,ReminderEventEntity::class], version=1, exportSchema=false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun placeDao(): PlaceDao
    abstract fun taskPlaceLinkDao(): TaskPlaceLinkDao
    abstract fun activeGeofenceDao(): ActiveGeofenceDao
    abstract fun reminderEventDao(): ReminderEventDao
}
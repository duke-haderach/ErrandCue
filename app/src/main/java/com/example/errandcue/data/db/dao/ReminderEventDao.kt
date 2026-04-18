package com.example.errandcue.data.db.dao
import androidx.room.*
import com.example.errandcue.data.db.entity.ReminderEventEntity
import kotlinx.coroutines.flow.Flow
@Dao interface ReminderEventDao {
    @Insert suspend fun insert(event: ReminderEventEntity): Long
    @Query("SELECT * FROM reminder_events ORDER BY eventTime DESC") fun observeAll(): Flow<List<ReminderEventEntity>>
    @Query("SELECT * FROM reminder_events WHERE placeId=:placeId ORDER BY eventTime DESC LIMIT 1") suspend fun getLatestForPlace(placeId: Long): ReminderEventEntity?
}
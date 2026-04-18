package com.example.errandcue.data.repository
import com.example.errandcue.data.db.dao.ReminderEventDao; import com.example.errandcue.data.db.entity.ReminderEventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject; import javax.inject.Singleton
@Singleton class ReminderRepository @Inject constructor(private val dao: ReminderEventDao) {
    suspend fun logEvent(event: ReminderEventEntity) = dao.insert(event)
    fun observeAll(): Flow<List<ReminderEventEntity>> = dao.observeAll()
    suspend fun latestForPlace(placeId: Long): ReminderEventEntity? = dao.getLatestForPlace(placeId)
}
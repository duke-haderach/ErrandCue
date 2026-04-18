package com.example.errandcue.data.repository
import com.example.errandcue.data.db.dao.TaskPlaceLinkDao; import com.example.errandcue.data.db.entity.TaskPlaceLinkEntity
import javax.inject.Inject; import javax.inject.Singleton
@Singleton class TaskPlaceLinkRepository @Inject constructor(private val dao: TaskPlaceLinkDao) {
    suspend fun upsert(link: TaskPlaceLinkEntity) = dao.upsert(link)
}
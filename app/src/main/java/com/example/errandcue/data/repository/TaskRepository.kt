package com.example.errandcue.data.repository
import com.example.errandcue.data.db.dao.TaskDao; import com.example.errandcue.data.db.entity.TaskEntity
import javax.inject.Inject; import javax.inject.Singleton
@Singleton class TaskRepository @Inject constructor(private val dao: TaskDao) {
    suspend fun upsert(task: TaskEntity): Long = dao.upsert(task)
}
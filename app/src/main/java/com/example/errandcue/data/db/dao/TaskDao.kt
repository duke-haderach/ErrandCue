package com.example.errandcue.data.db.dao
import androidx.room.*
import com.example.errandcue.data.db.entity.TaskEntity
import com.example.errandcue.data.db.relation.TaskWithPlaces
import kotlinx.coroutines.flow.Flow
@Dao interface TaskDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun upsert(task: TaskEntity): Long
    @Transaction @Query("SELECT * FROM tasks WHERE status='ACTIVE' ORDER BY updatedAt DESC") fun observeActiveTasksWithPlaces(): Flow<List<TaskWithPlaces>>
    @Query("SELECT * FROM tasks ORDER BY updatedAt DESC") fun observeAllTasks(): Flow<List<TaskEntity>>
}
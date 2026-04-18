package com.example.errandcue.data.repository
import com.example.errandcue.data.db.dao.TaskDao; import com.example.errandcue.domain.model.HomeTaskItem
import kotlinx.coroutines.flow.Flow; import kotlinx.coroutines.flow.map
import javax.inject.Inject; import javax.inject.Singleton
@Singleton class HomeRepository @Inject constructor(private val taskDao: TaskDao) {
    fun observeHomeItems(): Flow<List<HomeTaskItem>> = taskDao.observeActiveTasksWithPlaces().map { rows ->
        rows.map { row -> HomeTaskItem(id=row.task.id, title=row.task.title, placeName=row.places.firstOrNull()?.name, placeId=row.places.firstOrNull()?.id, triggerType=row.task.triggerType, travelMode=row.task.travelMode) }
    }
}
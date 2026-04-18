package com.example.errandcue.data.repository
import com.example.errandcue.data.db.dao.PlaceDao; import com.example.errandcue.data.db.entity.PlaceEntity
import com.example.errandcue.data.db.relation.PlaceWithTasks; import kotlinx.coroutines.flow.Flow
import javax.inject.Inject; import javax.inject.Singleton
@Singleton class PlaceRepository @Inject constructor(private val dao: PlaceDao) {
    fun observePlaces(): Flow<List<PlaceEntity>> = dao.observePlaces()
    suspend fun upsert(place: PlaceEntity): Long = dao.upsert(place)
    suspend fun getPlaceWithTasks(placeId: Long): PlaceWithTasks? = dao.getPlaceWithTasks(placeId)
}
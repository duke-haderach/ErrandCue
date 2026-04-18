package com.example.errandcue.data.db.dao
import androidx.room.*
import com.example.errandcue.data.db.entity.PlaceEntity
import com.example.errandcue.data.db.relation.PlaceWithTasks
import kotlinx.coroutines.flow.Flow
@Dao interface PlaceDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun upsert(place: PlaceEntity): Long
    @Query("SELECT * FROM places ORDER BY updatedAt DESC") fun observePlaces(): Flow<List<PlaceEntity>>
    @Transaction @Query("SELECT * FROM places WHERE id=:placeId LIMIT 1") suspend fun getPlaceWithTasks(placeId: Long): PlaceWithTasks?
}
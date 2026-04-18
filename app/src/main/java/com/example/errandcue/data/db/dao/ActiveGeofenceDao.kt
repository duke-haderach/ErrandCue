package com.example.errandcue.data.db.dao
import androidx.room.*
import com.example.errandcue.data.db.entity.ActiveGeofenceEntity
import kotlinx.coroutines.flow.Flow
@Dao interface ActiveGeofenceDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun upsert(g: ActiveGeofenceEntity)
    @Query("SELECT * FROM active_geofences") fun observeAll(): Flow<List<ActiveGeofenceEntity>>
    @Query("DELETE FROM active_geofences WHERE placeId=:placeId") suspend fun delete(placeId: Long)
}
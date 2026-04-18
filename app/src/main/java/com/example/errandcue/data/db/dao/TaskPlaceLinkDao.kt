package com.example.errandcue.data.db.dao
import androidx.room.*
import com.example.errandcue.data.db.entity.TaskPlaceLinkEntity
@Dao interface TaskPlaceLinkDao {
    @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun upsert(link: TaskPlaceLinkEntity)
}
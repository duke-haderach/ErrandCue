package com.example.errandcue.di
import android.content.Context; import androidx.room.Room
import com.example.errandcue.data.db.AppDatabase
import com.example.errandcue.data.db.dao.*
import dagger.Module; import dagger.Provides; import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext; import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module @InstallIn(SingletonComponent::class) object AppModule {
    @Provides @Singleton fun provideDb(@ApplicationContext ctx: Context): AppDatabase = Room.databaseBuilder(ctx, AppDatabase::class.java, "errandcue.db").build()
    @Provides fun taskDao(db: AppDatabase): TaskDao = db.taskDao()
    @Provides fun placeDao(db: AppDatabase): PlaceDao = db.placeDao()
    @Provides fun taskPlaceLinkDao(db: AppDatabase): TaskPlaceLinkDao = db.taskPlaceLinkDao()
    @Provides fun activeGeofenceDao(db: AppDatabase): ActiveGeofenceDao = db.activeGeofenceDao()
    @Provides fun reminderEventDao(db: AppDatabase): ReminderEventDao = db.reminderEventDao()
}
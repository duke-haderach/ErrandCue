package com.example.errandcue.reminders.worker
import android.content.Context
import androidx.hilt.work.HiltWorker; import androidx.work.CoroutineWorker; import androidx.work.WorkerParameters
import com.example.errandcue.data.db.entity.ReminderEventEntity; import com.example.errandcue.data.repository.ReminderRepository
import dagger.assisted.Assisted; import dagger.assisted.AssistedInject
@HiltWorker class ReminderActionWorker @AssistedInject constructor(
    @Assisted ctx: Context, @Assisted params: WorkerParameters, private val repo: ReminderRepository
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        repo.logEvent(ReminderEventEntity(taskId=inputData.getLong("task_id",-1), placeId=inputData.getLong("place_id",-1), triggerSource="ACTION", decision=inputData.getString("action_type")?:"UNKNOWN", spoken=false, userAction=inputData.getString("action_type"), eventTime=System.currentTimeMillis()))
        return Result.success()
    }
}
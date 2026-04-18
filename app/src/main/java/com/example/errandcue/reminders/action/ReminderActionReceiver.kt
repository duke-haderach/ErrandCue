package com.example.errandcue.reminders.action
import android.content.BroadcastReceiver; import android.content.Context; import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder; import androidx.work.WorkManager; import androidx.work.workDataOf
import com.example.errandcue.reminders.worker.ReminderActionWorker
class ReminderActionReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        WorkManager.getInstance(ctx).enqueue(
            OneTimeWorkRequestBuilder<ReminderActionWorker>().setInputData(
                workDataOf("place_id" to intent.getLongExtra("place_id", -1), "task_id" to intent.getLongExtra("task_id", -1), "action_type" to intent.getStringExtra("action_type").orEmpty())
            ).build()
        )
    }
}
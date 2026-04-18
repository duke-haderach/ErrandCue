package com.example.errandcue.reminders.notification
import android.app.NotificationChannel; import android.app.NotificationManager; import android.app.PendingIntent; import android.content.Context; import android.content.Intent
import androidx.core.app.NotificationCompat; import androidx.core.app.NotificationManagerCompat
import com.example.errandcue.reminders.action.ReminderActionReceiver
import dagger.hilt.android.qualifiers.ApplicationContext; import javax.inject.Inject; import javax.inject.Singleton
@Singleton class ReminderNotificationManager @Inject constructor(@ApplicationContext private val ctx: Context) {
    private val channelId = "errandcue_reminders"
    fun ensureChannel() {
        val mgr = ctx.getSystemService(NotificationManager::class.java)
        mgr.createNotificationChannel(NotificationChannel(channelId, "Errand reminders", NotificationManager.IMPORTANCE_DEFAULT))
    }
    fun showReminder(placeId: Long, taskId: Long, title: String, body: String) {
        ensureChannel()
        val done   = actionPi(placeId, taskId, "DONE",  1000 + placeId.toInt())
        val snooze = actionPi(placeId, taskId, "SNOOZE", 2000 + placeId.toInt())
        val n = NotificationCompat.Builder(ctx, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title).setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true)
            .addAction(0, "Done", done).addAction(0, "Snooze", snooze).build()
        NotificationManagerCompat.from(ctx).notify(placeId.toInt().coerceAtLeast(1), n)
    }
    private fun actionPi(placeId: Long, taskId: Long, action: String, code: Int): PendingIntent {
        val i = Intent(ctx, ReminderActionReceiver::class.java).apply { putExtra("place_id", placeId); putExtra("task_id", taskId); putExtra("action_type", action) }
        return PendingIntent.getBroadcast(ctx, code, i, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
}
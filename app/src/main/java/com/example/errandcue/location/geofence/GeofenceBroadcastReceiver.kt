package com.example.errandcue.location.geofence
import android.content.BroadcastReceiver; import android.content.Context; import android.content.Intent; import android.util.Log
import com.example.errandcue.reminders.worker.ReminderDispatchService
import com.google.android.gms.location.GeofencingEvent
class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        val event = GeofencingEvent.fromIntent(intent) ?: return
        if (event.hasError()) { Log.e("ErrandCue", "Geofence error: ${event.errorCode}"); return }
        ctx.startService(Intent(ctx, ReminderDispatchService::class.java).apply {
            putExtra("transition", event.geofenceTransition)
            putStringArrayListExtra("request_ids", ArrayList(event.triggeringGeofences?.map { it.requestId }.orEmpty()))
        })
    }
}
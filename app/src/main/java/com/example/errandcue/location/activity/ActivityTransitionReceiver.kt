package com.example.errandcue.location.activity
import android.content.BroadcastReceiver; import android.content.Context; import android.content.Intent; import android.util.Log
import com.google.android.gms.location.ActivityTransitionResult
class ActivityTransitionReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent) ?: return
            result.transitionEvents.forEach { Log.d("ErrandCue", "Activity: ${it.activityType} -> ${it.transitionType}") }
        }
    }
}
package com.example.errandcue.reminders.worker
import android.app.Service; import android.content.Intent; import android.os.IBinder; import android.util.Log
import com.example.errandcue.core.time.CooldownPolicy
import com.example.errandcue.data.db.entity.ReminderEventEntity
import com.example.errandcue.data.repository.PlaceRepository; import com.example.errandcue.data.repository.ReminderRepository
import com.example.errandcue.reminders.engine.ReminderResolver
import com.example.errandcue.reminders.notification.ReminderNotificationManager
import com.example.errandcue.reminders.tts.ReminderSpeaker
import com.example.errandcue.settings.SettingsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*; import kotlinx.coroutines.flow.first
import javax.inject.Inject
@AndroidEntryPoint class ReminderDispatchService : Service() {
    @Inject lateinit var placeRepo: PlaceRepository
    @Inject lateinit var reminderRepo: ReminderRepository
    @Inject lateinit var resolver: ReminderResolver
    @Inject lateinit var notifMgr: ReminderNotificationManager
    @Inject lateinit var speaker: ReminderSpeaker
    @Inject lateinit var cooldown: CooldownPolicy
    @Inject lateinit var settings: SettingsRepository
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val transition = intent?.getIntExtra("transition", -1) ?: -1
        val ids = intent?.getStringArrayListExtra("request_ids").orEmpty()
        scope.launch {
            val now = System.currentTimeMillis()
            val prefs = settings.settings.first()
            ids.forEach { id ->
                val placeId = id.toLongOrNull() ?: return@forEach
                val last = reminderRepo.latestForPlace(placeId)
                if (prefs.cooldownEnabled && !cooldown.canTrigger(now, last?.eventTime)) return@forEach
                val pwt = placeRepo.getPlaceWithTasks(placeId) ?: return@forEach
                val resolved = resolver.resolve(pwt.place.id, pwt.place.name, pwt.tasks.map { it.id to (it.voiceText ?: it.title) })
                reminderRepo.logEvent(ReminderEventEntity(taskId=resolved.taskIds.firstOrNull()?:-1, placeId=resolved.placeId, triggerSource="GEOFENCE", decision=when(transition){1->"ENTER";2->"EXIT";4->"DWELL";else->"UNKNOWN"}, spoken=prefs.speechEnabled, userAction=null, eventTime=now))
                notifMgr.showReminder(resolved.placeId, resolved.taskIds.firstOrNull()?:-1, resolved.notificationTitle, resolved.notificationBody)
                if (prefs.speechEnabled) speaker.speak(resolved.speechText)
                Log.d("ErrandCue", "Dispatched: ${resolved.placeName}")
            }
            stopSelf(startId)
        }
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onDestroy() { scope.cancel(); super.onDestroy() }
}
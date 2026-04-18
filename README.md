# ErrandCue 📍

> **Stop making extra trips. Get reminded when you're already there.**

ErrandCue is an Android app that attaches to-do tasks to real-world places — a UPS store, the library, Kohl's — and delivers **spoken + visual reminders** the moment you're passing through or arriving. No always-on tracking. No separate navigation system required.

---

## The problem it solves

You need to return a package. You drive past UPS. You forget. You make a separate trip later.

ErrandCue solves this by watching places you care about and speaking your errand list aloud the moment you're near, using Android's geofencing so it's accurate and battery-friendly.

---

## Features

| Feature | Detail |
|---|---|
| 📝 Task buckets per place | Link multiple tasks to one location |
| 📍 Geofence triggers | Fires on arrive or dwell |
| 🔊 TTS reminders | Spoken aloud while driving — no screen needed |
| 🔔 Done / Snooze actions | One-tap from the notification |
| 🕐 Cooldown protection | 15-min quiet zone per place |
| 📜 Reminder history | Full log of every event |
| ⚙️ Persistent settings | DataStore-backed, survives restarts |
| 🗺️ Google Maps handoff | Launch navigation to any saved place |

---

## Tech stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose + Material 3 |
| State | ViewModel + Kotlin Flow + StateFlow |
| DB | Room — entities, DAOs, many-to-many @Relation |
| Settings | DataStore Preferences |
| Location | Google Play Services Geofencing + FusedLocationProvider |
| Background | BroadcastReceiver + Service + WorkManager |
| Speech | Android TextToSpeech |
| DI | Hilt |
| Navigation | Compose Navigation |

---

## Architecture

```
feature/
  home/           HomeScreen · HomeViewModel
  taskedit/       TaskEditScreen · TaskEditViewModel
  history/        HistoryScreen · HistoryViewModel
  settings/       SettingsScreen · SettingsViewModel

data/
  db/             AppDatabase, entities, DAOs, Room relations
  repository/     TaskRepository, PlaceRepository, HomeRepository,
                  ReminderRepository, TaskPlaceLinkRepository

domain/model/     HomeTaskItem, ResolvedReminder
settings/         SettingsRepository (DataStore)

location/
  geofence/       GeofenceManager · GeofenceBroadcastReceiver
  activity/       ActivityTransitionReceiver
  fused/          CurrentLocationClient

reminders/
  action/         ReminderActionReceiver (Done / Snooze)
  engine/         ReminderResolver
  notification/   ReminderNotificationManager
  tts/            ReminderSpeaker
  worker/         ReminderDispatchService · ReminderActionWorker

maps/             MapsLauncher
di/               AppModule (Hilt)
core/             PermissionManager · CooldownPolicy
app/              ErrandCueApp · MainActivity · AppNavGraph
```

---

## Getting started

1. Clone the repo
2. Open in **Android Studio Hedgehog** (2023.1.1) or later
3. Add your `google-services.json` to `app/`
4. Run on a physical device (Android 10+, API 29+) — geofences require real GPS
5. Grant all permissions when prompted
6. Create a task → set place coordinates → tap **Save & arm**

---

## Permissions

| Permission | Purpose |
|---|---|
| `ACCESS_FINE_LOCATION` | Accurate geofence triggers |
| `ACCESS_BACKGROUND_LOCATION` | Reminder fires when screen is off |
| `ACTIVITY_RECOGNITION` | Future: motion-gated speech |
| `POST_NOTIFICATIONS` | Show reminder cards |

---

## Known limitations / next steps

- Driving-only speech is a setting toggle but not yet wired to motion-gated dispatch
- Route-aware "passing through" scoring is not implemented (pure radius geofence today)
- Snooze currently logs a WorkManager event but does not reschedule to a specific time
- No unit or integration tests yet
- `google-services.json` not included (add your own from Firebase Console)

---

## License

MIT

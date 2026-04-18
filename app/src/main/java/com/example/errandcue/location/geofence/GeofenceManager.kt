package com.example.errandcue.location.geofence
import android.annotation.SuppressLint; import android.app.PendingIntent; import android.content.Context; import android.content.Intent
import com.example.errandcue.data.db.entity.PlaceEntity
import com.google.android.gms.location.Geofence; import com.google.android.gms.location.GeofencingRequest; import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext; import javax.inject.Inject; import javax.inject.Singleton
@Singleton class GeofenceManager @Inject constructor(@ApplicationContext private val ctx: Context) {
    private val client = LocationServices.getGeofencingClient(ctx)
    private fun pendingIntent(): PendingIntent {
        val intent = Intent(ctx, GeofenceBroadcastReceiver::class.java)
        return PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
    }
    @SuppressLint("MissingPermission")
    fun armPlaces(places: List<PlaceEntity>) {
        if (places.isEmpty()) return
        val geofences = places.map { Geofence.Builder().setRequestId(it.id.toString()).setCircularRegion(it.latitude, it.longitude, it.radiusMeters).setExpirationDuration(Geofence.NEVER_EXPIRE).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL).setLoiteringDelay(30_000).build() }
        val request = GeofencingRequest.Builder().setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER).addGeofences(geofences).build()
        client.addGeofences(request, pendingIntent())
    }
}
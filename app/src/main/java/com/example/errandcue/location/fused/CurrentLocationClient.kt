package com.example.errandcue.location.fused
import android.annotation.SuppressLint; import android.content.Context
import com.google.android.gms.location.CurrentLocationRequest; import com.google.android.gms.location.LocationServices; import com.google.android.gms.tasks.Task
import dagger.hilt.android.qualifiers.ApplicationContext; import javax.inject.Inject; import javax.inject.Singleton
@Singleton class CurrentLocationClient @Inject constructor(@ApplicationContext ctx: Context) {
    private val client = LocationServices.getFusedLocationProviderClient(ctx)
    @SuppressLint("MissingPermission")
    fun get(): Task<android.location.Location> = client.getCurrentLocation(CurrentLocationRequest.Builder().build(), null)
}
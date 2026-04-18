package com.example.errandcue.core.permissions
import android.Manifest; import android.content.Context; import android.content.pm.PackageManager; import android.os.Build
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext; import javax.inject.Inject; import javax.inject.Singleton
@Singleton class PermissionManager @Inject constructor(@ApplicationContext private val ctx: Context) {
    fun hasForeground() = has(Manifest.permission.ACCESS_FINE_LOCATION) && has(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun hasBackground() = if (Build.VERSION.SDK_INT >= 29) has(Manifest.permission.ACCESS_BACKGROUND_LOCATION) else true
    fun hasActivity()   = if (Build.VERSION.SDK_INT >= 29) has(Manifest.permission.ACTIVITY_RECOGNITION) else true
    fun hasNotif()      = if (Build.VERSION.SDK_INT >= 33) has(Manifest.permission.POST_NOTIFICATIONS) else true
    private fun has(p: String) = ContextCompat.checkSelfPermission(ctx, p) == PackageManager.PERMISSION_GRANTED
}
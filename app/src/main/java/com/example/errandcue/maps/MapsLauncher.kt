package com.example.errandcue.maps
import android.content.Context; import android.content.Intent; import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext; import javax.inject.Inject; import javax.inject.Singleton
@Singleton class MapsLauncher @Inject constructor(@ApplicationContext private val ctx: Context) {
    fun navigate(lat: Double, lng: Double) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$lat,$lng")).apply { setPackage("com.google.android.apps.maps"); addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
        ctx.startActivity(i)
    }
}
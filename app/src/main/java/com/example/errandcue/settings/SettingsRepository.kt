package com.example.errandcue.settings
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey; import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow; import kotlinx.coroutines.flow.map
import javax.inject.Inject; import javax.inject.Singleton
private val Context.dataStore by preferencesDataStore("errandcue_settings")
@Singleton class SettingsRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private object Keys {
        val speechEnabled = booleanPreferencesKey("speech_enabled")
        val drivingOnly   = booleanPreferencesKey("driving_only")
        val cooldown      = booleanPreferencesKey("cooldown_enabled")
        val mapsHandoff   = booleanPreferencesKey("maps_handoff")
    }
    data class Settings(val speechEnabled: Boolean=true, val drivingOnly: Boolean=false, val cooldownEnabled: Boolean=true, val mapsHandoff: Boolean=true)
    val settings: Flow<Settings> = context.dataStore.data.map { p -> Settings(p[Keys.speechEnabled]?:true, p[Keys.drivingOnly]?:false, p[Keys.cooldown]?:true, p[Keys.mapsHandoff]?:true) }
    suspend fun setSpeechEnabled(v: Boolean) = context.dataStore.edit { it[Keys.speechEnabled]=v }
    suspend fun setDrivingOnly(v: Boolean)   = context.dataStore.edit { it[Keys.drivingOnly]=v }
    suspend fun setCooldown(v: Boolean)      = context.dataStore.edit { it[Keys.cooldown]=v }
    suspend fun setMapsHandoff(v: Boolean)   = context.dataStore.edit { it[Keys.mapsHandoff]=v }
}
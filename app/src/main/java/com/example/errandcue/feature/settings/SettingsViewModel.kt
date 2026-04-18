package com.example.errandcue.feature.settings
import androidx.lifecycle.ViewModel; import androidx.lifecycle.viewModelScope
import com.example.errandcue.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*; import kotlinx.coroutines.launch; import javax.inject.Inject
@HiltViewModel class SettingsViewModel @Inject constructor(private val repo: SettingsRepository) : ViewModel() {
    val settings: StateFlow<SettingsRepository.Settings> = repo.settings.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsRepository.Settings())
    fun setSpeech(v:Boolean) = viewModelScope.launch{repo.setSpeechEnabled(v)}
    fun setDriving(v:Boolean)= viewModelScope.launch{repo.setDrivingOnly(v)}
    fun setCooldown(v:Boolean)=viewModelScope.launch{repo.setCooldown(v)}
    fun setMaps(v:Boolean)   = viewModelScope.launch{repo.setMapsHandoff(v)}
}
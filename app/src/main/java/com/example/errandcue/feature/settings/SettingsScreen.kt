package com.example.errandcue.feature.settings
import androidx.compose.foundation.layout.*; import androidx.compose.material3.*; import androidx.compose.runtime.*
import androidx.compose.ui.Modifier; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel; import androidx.lifecycle.compose.collectAsStateWithLifecycle
@Composable fun SettingsScreen(onBack: ()->Unit, vm: SettingsViewModel = hiltViewModel()) {
    val s by vm.settings.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().padding(24.dp), Arrangement.spacedBy(16.dp)) {
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("Settings", style=MaterialTheme.typography.headlineSmall); TextButton(onBack){Text("Back")} }
        ToggleRow("Spoken reminders",            s.speechEnabled)         { vm.setSpeech(it) }
        ToggleRow("Only speak while driving",    s.drivingOnly)           { vm.setDriving(it) }
        ToggleRow("Cooldown (15 min quiet zone)", s.cooldownEnabled)      { vm.setCooldown(it) }
        ToggleRow("Google Maps handoff",          s.mapsHandoff)          { vm.setMaps(it) }
    }
}
@Composable private fun ToggleRow(label: String, checked: Boolean, onChange: (Boolean)->Unit) {
    Card(Modifier.fillMaxWidth()) { Row(Modifier.padding(horizontal=16.dp, vertical=12.dp), Arrangement.SpaceBetween) { Text(label, Modifier.weight(1f)); Switch(checked, onChange) } }
}
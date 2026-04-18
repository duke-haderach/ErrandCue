package com.example.errandcue.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit, vm: SettingsViewModel = hiltViewModel()) {
    val s by vm.settings.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionLabel("Reminders")
            SettingsToggleCard(
                icon = Icons.Filled.Phone,
                iconTint = Color(0xFF1565C0),
                title = "Spoken reminders",
                subtitle = "Read your tasks aloud when triggered",
                checked = s.speechEnabled,
                onToggle = { vm.setSpeech(it) }
            )
            SettingsToggleCard(
                icon = Icons.Filled.Settings,
                iconTint = Color(0xFFE65100),
                title = "Driving mode only",
                subtitle = "Only speak while you are driving",
                checked = s.drivingOnly,
                onToggle = { vm.setDriving(it) }
            )

            SectionLabel("Behavior")
            SettingsToggleCard(
                icon = Icons.Filled.Notifications,
                iconTint = Color(0xFF6A1B9A),
                title = "15-min cooldown",
                subtitle = "Silence repeats within 15 minutes per place",
                checked = s.cooldownEnabled,
                onToggle = { vm.setCooldown(it) }
            )
            SettingsToggleCard(
                icon = Icons.Filled.LocationOn,
                iconTint = Color(0xFF2E7D32),
                title = "Google Maps handoff",
                subtitle = "Launch navigation when near a saved place",
                checked = s.mapsHandoff,
                onToggle = { vm.setMaps(it) }
            )

            Spacer(Modifier.height(16.dp))
            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("ErrandCue", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Version 0.1.0  •  Location-aware errand reminders", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun SectionLabel(text: String) {
    Text(text, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 4.dp))
}

@Composable
fun SettingsToggleCard(icon: ImageVector, iconTint: Color, title: String, subtitle: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(1.dp), shape = RoundedCornerShape(12.dp)) {
        Row(
            Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(icon, null, tint = iconTint, modifier = Modifier.size(24.dp))
            Column(Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Switch(checked, onToggle)
        }
    }
}

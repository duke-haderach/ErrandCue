package com.example.errandcue.feature.home
import android.Manifest; import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*; import androidx.compose.runtime.*
import androidx.compose.ui.Modifier; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel; import androidx.lifecycle.compose.collectAsStateWithLifecycle
@Composable fun HomeScreen(onAddTask: ()->Unit, onSettings: ()->Unit, onHistory: ()->Unit, vm: HomeViewModel = hiltViewModel()) {
    val st by vm.uiState.collectAsStateWithLifecycle()
    val fgL = rememberLauncherForActivityResult(RequestMultiplePermissions()) {}
    val bgL = rememberLauncherForActivityResult(RequestPermission()) {}
    val arL = rememberLauncherForActivityResult(RequestPermission()) {}
    val ntL = rememberLauncherForActivityResult(RequestPermission()) {}
    LazyColumn(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item { Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("ErrandCue", style=MaterialTheme.typography.headlineMedium); Row { TextButton(onHistory){Text("History")}; TextButton(onSettings){Text("Settings")} } } }
        item { Text("Attach tasks to places. Get reminded when you're nearby.", color=MaterialTheme.colorScheme.onSurfaceVariant) }
        item { Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp), Arrangement.spacedBy(6.dp)) { Text("Active tasks: ${st.items.size}"); Text("Speech: ${st.settings.speechEnabled} • Cooldown: ${st.settings.cooldownEnabled} • Maps: ${st.settings.mapsHandoff}") } } }
        item { Row(horizontalArrangement=Arrangement.spacedBy(10.dp)) {
            Button({fgL.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION))}){Text("Foreground loc")}
            if(Build.VERSION.SDK_INT>=29) { Button({bgL.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)}){Text("Background")}; Button({arL.launch(Manifest.permission.ACTIVITY_RECOGNITION)}){Text("Motion")} }
            if(Build.VERSION.SDK_INT>=33) Button({ntL.launch(Manifest.permission.POST_NOTIFICATIONS)}){Text("Notifications")}
        }}
        item { Button(onAddTask){ Text("＋ Add task") } }
        item { Text("Saved tasks", style=MaterialTheme.typography.titleMedium) }
        if (st.items.isEmpty()) item { Text("No tasks yet.") }
        else items(st.items, key={it.id}) { task -> Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp), Arrangement.spacedBy(6.dp)) { Text(task.title, style=MaterialTheme.typography.titleSmall); Text("Place: ${task.placeName ?: "—"}  •  ${task.triggerType}", color=MaterialTheme.colorScheme.onSurfaceVariant) } } }
    }
}
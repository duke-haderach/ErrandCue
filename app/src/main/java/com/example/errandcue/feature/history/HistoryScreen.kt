package com.example.errandcue.feature.history
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Modifier; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel; import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat; import java.util.Date; import java.util.Locale
@Composable fun HistoryScreen(onBack: ()->Unit, vm: HistoryViewModel = hiltViewModel()) {
    val events by vm.events.collectAsStateWithLifecycle()
    val fmt = remember { SimpleDateFormat("MMM d, HH:mm", Locale.US) }
    LazyColumn(Modifier.fillMaxSize().padding(24.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
        item { Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) { Text("Reminder history", style=MaterialTheme.typography.headlineSmall); TextButton(onBack){Text("Back")} } }
        if (events.isEmpty()) item { Text("No reminder events yet.") }
        else items(events, key={it.id}) { e -> Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp), Arrangement.spacedBy(4.dp)) { Text("Place ID: ${e.placeId}", style=MaterialTheme.typography.titleSmall); Text("${e.decision} via ${e.triggerSource} • spoken: ${e.spoken}"); Text(fmt.format(Date(e.eventTime)), color=MaterialTheme.colorScheme.onSurfaceVariant) } } }
    }
}
package com.example.errandcue.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.errandcue.data.db.entity.ReminderEventEntity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(onBack: () -> Unit, vm: HistoryViewModel = hiltViewModel()) {
    val events by vm.events.collectAsStateWithLifecycle()
    val fmt = remember { SimpleDateFormat("MMM d, h:mm a", Locale.US) }

    // Demo events for empty state
    val demoEvents = remember {
        val now = System.currentTimeMillis()
        listOf(
            ReminderEventEntity(1, 1, 1, "GEOFENCE", "ENTER", true, null, now - 3_600_000),
            ReminderEventEntity(2, 2, 2, "GEOFENCE", "DWELL", false, "DONE", now - 7_200_000),
            ReminderEventEntity(3, 3, 3, "GEOFENCE", "ENTER", true, "SNOOZE", now - 86_400_000),
            ReminderEventEntity(4, 4, 4, "GEOFENCE", "ENTER", true, "DONE", now - 172_800_000)
        )
    }
    val displayEvents = if (events.isEmpty()) demoEvents else events

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reminder History", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            if (displayEvents.isEmpty()) {
                item {
                    Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Filled.Notifications, null, Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("No reminders yet", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            } else {
                items(displayEvents, key = { it.id }) { e ->
                    HistoryEventCard(e, fmt)
                }
            }
        }
    }
}

@Composable
fun HistoryEventCard(e: ReminderEventEntity, fmt: SimpleDateFormat) {
    val decisionColor = when (e.decision) {
        "ENTER" -> Color(0xFF1565C0)
        "DWELL" -> Color(0xFF6A1B9A)
        "EXIT"  -> Color(0xFF424242)
        else    -> Color.Gray
    }
    val actionColor = when (e.userAction) {
        "DONE"   -> Color(0xFF2E7D32)
        "SNOOZE" -> Color(0xFFE65100)
        else     -> Color.Gray
    }

    Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp), shape = RoundedCornerShape(12.dp)) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                Modifier.size(42.dp).clip(RoundedCornerShape(10.dp)).background(decisionColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.LocationOn, null, tint = decisionColor, modifier = Modifier.size(22.dp))
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text("Place #${e.placeId}", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                    MiniEventChip(e.decision, decisionColor)
                    if (e.spoken) MiniEventChip("🔊 Spoken", Color(0xFF1565C0))
                    if (e.userAction != null) MiniEventChip(e.userAction, actionColor)
                }
                Text(fmt.format(Date(e.eventTime)), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun MiniEventChip(label: String, color: Color) {
    Box(
        Modifier.clip(RoundedCornerShape(4.dp)).background(color.copy(alpha = 0.12f)).padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(label, fontSize = 10.sp, color = color, fontWeight = FontWeight.Medium)
    }
}

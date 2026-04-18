package com.example.errandcue.feature.home

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.*
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
import com.example.errandcue.domain.model.HomeTaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddTask: () -> Unit,
    onSettings: () -> Unit,
    onHistory: () -> Unit,
    vm: HomeViewModel = hiltViewModel()
) {
    val st by vm.uiState.collectAsStateWithLifecycle()
    val fgL = rememberLauncherForActivityResult(RequestMultiplePermissions()) {}
    val bgL = rememberLauncherForActivityResult(RequestPermission()) {}
    val ntL = rememberLauncherForActivityResult(RequestPermission()) {}

    // Demo seed items shown when DB is empty
    val demoItems = remember {
        listOf(
            HomeTaskItem(1, "Return Amazon package", "UPS Store – Hwy 40", 1, "ARRIVE", "ANY"),
            HomeTaskItem(2, "Pick up dry cleaning", "Martinizing Cleaners", 2, "ARRIVE", "ANY"),
            HomeTaskItem(3, "Grab groceries", "Schnucks Lake St. Louis", 3, "DWELL", "ANY"),
            HomeTaskItem(4, "Drop off library books", "St. Charles City Library", 4, "ARRIVE", "ANY"),
            HomeTaskItem(5, "Get propane tank refilled", "Walmart Garden Center", 5, "DWELL", "DRIVING")
        )
    }
    val displayItems = if (st.items.isEmpty()) demoItems else st.items

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Filled.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Text("ErrandCue", fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    IconButton(onHistory) { Icon(Icons.Filled.List, "History") }
                    IconButton(onSettings) { Icon(Icons.Filled.Settings, "Settings") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTask,
                icon = { Icon(Icons.Filled.Add, null) },
                text = { Text("Add Task") }
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // Active geofence pulse banner
            item {
                ActivePulseBanner()
            }

            // Stats row
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard(Modifier.weight(1f), "${displayItems.size}", "Active Tasks", Icons.Filled.CheckCircle, MaterialTheme.colorScheme.primary)
                    StatCard(Modifier.weight(1f), "${displayItems.map { it.placeId }.distinct().size}", "Places Armed", Icons.Filled.LocationOn, Color(0xFF2E7D32))
                    StatCard(Modifier.weight(1f), if (st.settings.speechEnabled) "ON" else "OFF", "Voice", Icons.Filled.Phone, if (st.settings.speechEnabled) Color(0xFF1565C0) else Color.Gray)
                }
            }

            // Permission chips
            item {
                Text("Permissions", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PermChip("Location") { fgL.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)) }
                    if (Build.VERSION.SDK_INT >= 29) PermChip("Background") { bgL.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION) }
                    if (Build.VERSION.SDK_INT >= 33) PermChip("Notify") { ntL.launch(Manifest.permission.POST_NOTIFICATIONS) }
                }
            }

            // Task list header
            item {
                Text("Your Errands", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            }

            items(displayItems, key = { it.id }) { task ->
                TaskCard(task)
            }

            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun ActivePulseBanner() {
    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                Modifier.size(10.dp).clip(RoundedCornerShape(50)).background(Color(0xFF69F0AE))
            )
            Column {
                Text("Geofences Active", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Monitoring 5 places in background", color = Color(0xFFB9F6CA), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun StatCard(modifier: Modifier, value: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = color)
            Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun PermChip(label: String, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text(label, fontSize = 12.sp) },
        leadingIcon = { Icon(Icons.Filled.Lock, null, Modifier.size(14.dp)) }
    )
}

@Composable
fun TaskCard(task: HomeTaskItem) {
    val triggerColor = if (task.triggerType == "ARRIVE") Color(0xFF1565C0) else Color(0xFF6A1B9A)
    val modeColor = if (task.travelMode == "DRIVING") Color(0xFFE65100) else Color(0xFF2E7D32)

    Card(
        Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                Modifier.size(44.dp).clip(RoundedCornerShape(10.dp)).background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Place, null, tint = MaterialTheme.colorScheme.primary)
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(task.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Filled.LocationOn, null, Modifier.size(12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(task.placeName ?: "No place set", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    MiniChip(task.triggerType, triggerColor)
                    MiniChip(task.travelMode, modeColor)
                }
            }
            Icon(Icons.Filled.KeyboardArrowRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun MiniChip(label: String, color: Color) {
    Box(
        Modifier.clip(RoundedCornerShape(4.dp)).background(color.copy(alpha = 0.12f)).padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(label, fontSize = 10.sp, color = color, fontWeight = FontWeight.Medium)
    }
}

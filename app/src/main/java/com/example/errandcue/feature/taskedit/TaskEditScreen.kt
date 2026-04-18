package com.example.errandcue.feature.taskedit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(onBack: () -> Unit, vm: TaskEditViewModel = hiltViewModel()) {
    var title  by remember { mutableStateOf("") }
    var notes  by remember { mutableStateOf("") }
    var voice  by remember { mutableStateOf("") }
    var place  by remember { mutableStateOf("") }
    var lat    by remember { mutableStateOf("38.8106") }
    var lng    by remember { mutableStateOf("-90.6998") }
    var radius by remember { mutableStateOf("150") }
    var triggerArrive by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Errand", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onBack) { Icon(Icons.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            )
        }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            // Task section
            SectionHeader(icon = Icons.Filled.CheckCircle, title = "Task Details")
            OutlinedTextField(title, { title = it }, Modifier.fillMaxWidth(), label = { Text("Task title *") }, leadingIcon = { Icon(Icons.Filled.Edit, null) }, singleLine = true)
            OutlinedTextField(notes, { notes = it }, Modifier.fillMaxWidth(), label = { Text("Notes") }, leadingIcon = { Icon(Icons.Filled.Info, null) }, minLines = 2)
            OutlinedTextField(voice, { voice = it }, Modifier.fillMaxWidth(), label = { Text("Spoken reminder text") }, placeholder = { Text("e.g. Return the Amazon package") }, leadingIcon = { Icon(Icons.Filled.Phone, null) }, minLines = 2)

            HorizontalDivider()

            // Place section
            SectionHeader(icon = Icons.Filled.LocationOn, title = "Place")
            OutlinedTextField(place, { place = it }, Modifier.fillMaxWidth(), label = { Text("Place name") }, leadingIcon = { Icon(Icons.Filled.Place, null) }, singleLine = true)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(lat, { lat = it }, Modifier.weight(1f), label = { Text("Latitude") })
                OutlinedTextField(lng, { lng = it }, Modifier.weight(1f), label = { Text("Longitude") })
            }
            OutlinedTextField(radius, { radius = it }, Modifier.fillMaxWidth(), label = { Text("Radius (meters)") }, leadingIcon = { Icon(Icons.Filled.Search, null) }, singleLine = true)

            HorizontalDivider()

            // Trigger section
            SectionHeader(icon = Icons.Filled.Notifications, title = "Trigger")
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FilterChip(triggerArrive, { triggerArrive = true }, label = { Text("On Arrive") }, Modifier.weight(1f), leadingIcon = if (triggerArrive) {{ Icon(Icons.Filled.Check, null, Modifier.size(16.dp)) }} else null)
                FilterChip(!triggerArrive, { triggerArrive = false }, label = { Text("On Dwell") }, Modifier.weight(1f), leadingIcon = if (!triggerArrive) {{ Icon(Icons.Filled.Check, null, Modifier.size(16.dp)) }} else null)
            }

            Spacer(Modifier.height(8.dp))

            // Save button
            Button(
                onClick = {
                    vm.save(title, notes, voice, place, lat.toDoubleOrNull() ?: 38.8106, lng.toDoubleOrNull() ?: -90.6998, radius.toFloatOrNull() ?: 150f)
                    onBack()
                },
                Modifier.fillMaxWidth().height(52.dp),
                enabled = title.isNotBlank()
            ) {
                Icon(Icons.Filled.CheckCircle, null)
                Spacer(Modifier.width(8.dp))
                Text("Save & Arm Geofence", fontWeight = FontWeight.SemiBold)
            }
            OutlinedButton(onBack, Modifier.fillMaxWidth()) { Text("Cancel") }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionHeader(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
        Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
    }
}

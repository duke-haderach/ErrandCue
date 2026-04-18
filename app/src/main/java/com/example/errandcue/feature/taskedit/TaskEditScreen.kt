package com.example.errandcue.feature.taskedit
import androidx.compose.foundation.layout.*; import androidx.compose.foundation.rememberScrollState; import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*; import androidx.compose.runtime.*; import androidx.compose.ui.Modifier; import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
@Composable fun TaskEditScreen(onBack: ()->Unit, vm: TaskEditViewModel = hiltViewModel()) {
    var title  by remember{mutableStateOf("")}; var notes by remember{mutableStateOf("")}; var voice by remember{mutableStateOf("")}
    var place  by remember{mutableStateOf("")}; var lat   by remember{mutableStateOf("38.8106")}; var lng  by remember{mutableStateOf("-90.6998")}; var radius by remember{mutableStateOf("150")}
    Column(Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()), Arrangement.spacedBy(14.dp)) {
        Text("Create task", style=MaterialTheme.typography.headlineSmall)
        OutlinedTextField(title,{title=it},Modifier.fillMaxWidth(),label={Text("Task title *")})
        OutlinedTextField(notes,{notes=it},Modifier.fillMaxWidth(),label={Text("Notes")})
        OutlinedTextField(voice,{voice=it},Modifier.fillMaxWidth(),label={Text("Voice reminder text")},placeholder={Text("e.g. Return the Amazon package")})
        HorizontalDivider(); Text("Place", style=MaterialTheme.typography.labelLarge)
        OutlinedTextField(place,{place=it},Modifier.fillMaxWidth(),label={Text("Place name")})
        Row(horizontalArrangement=Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(lat,{lat=it},Modifier.weight(1f),label={Text("Latitude")})
            OutlinedTextField(lng,{lng=it},Modifier.weight(1f),label={Text("Longitude")})
            OutlinedTextField(radius,{radius=it},Modifier.weight(1f),label={Text("Radius (m)")})
        }
        Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            Button({vm.save(title,notes,voice,place,lat.toDoubleOrNull()?:38.8106,lng.toDoubleOrNull()?:-90.6998,radius.toFloatOrNull()?:150f);onBack()}) { Text("Save & arm") }
            OutlinedButton(onBack){ Text("Cancel") }
        }
    }
}
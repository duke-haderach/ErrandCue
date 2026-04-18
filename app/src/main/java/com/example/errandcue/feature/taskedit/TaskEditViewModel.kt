package com.example.errandcue.feature.taskedit
import androidx.lifecycle.ViewModel; import androidx.lifecycle.viewModelScope
import com.example.errandcue.data.db.entity.*; import com.example.errandcue.data.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel; import kotlinx.coroutines.launch; import javax.inject.Inject
@HiltViewModel class TaskEditViewModel @Inject constructor(private val taskRepo: TaskRepository, private val placeRepo: PlaceRepository, private val linkRepo: TaskPlaceLinkRepository) : ViewModel() {
    fun save(title: String, notes: String, voice: String, place: String, lat: Double, lng: Double, radius: Float) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val tid = taskRepo.upsert(TaskEntity(title=title.ifBlank{"Untitled"}, notes=notes.ifBlank{null}, voiceText=voice.ifBlank{null}, createdAt=now, updatedAt=now))
            val pid = placeRepo.upsert(PlaceEntity(name=place.ifBlank{"Unnamed place"}, latitude=lat, longitude=lng, radiusMeters=radius, createdAt=now, updatedAt=now))
            linkRepo.upsert(TaskPlaceLinkEntity(tid, pid))
        }
    }
}
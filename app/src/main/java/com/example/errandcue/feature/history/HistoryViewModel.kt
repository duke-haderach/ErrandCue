package com.example.errandcue.feature.history
import androidx.lifecycle.ViewModel; import androidx.lifecycle.viewModelScope
import com.example.errandcue.data.db.entity.ReminderEventEntity; import com.example.errandcue.data.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*; import javax.inject.Inject
@HiltViewModel class HistoryViewModel @Inject constructor(repo: ReminderRepository) : ViewModel() {
    val events: StateFlow<List<ReminderEventEntity>> = repo.observeAll().map{it.take(30)}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
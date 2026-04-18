package com.example.errandcue.feature.home
import androidx.lifecycle.ViewModel; import androidx.lifecycle.viewModelScope
import com.example.errandcue.data.repository.HomeRepository; import com.example.errandcue.domain.model.HomeTaskItem
import com.example.errandcue.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*; import javax.inject.Inject
data class HomeUiState(val items: List<HomeTaskItem> = emptyList(), val settings: SettingsRepository.Settings = SettingsRepository.Settings())
@HiltViewModel class HomeViewModel @Inject constructor(home: HomeRepository, settingsRepo: SettingsRepository) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = combine(home.observeHomeItems(), settingsRepo.settings) { items, s -> HomeUiState(items, s) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())
}
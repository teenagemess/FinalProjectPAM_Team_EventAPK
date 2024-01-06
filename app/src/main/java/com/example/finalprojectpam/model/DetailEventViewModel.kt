package com.example.finalprojectpam.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.repositori.RepositoriEvent
import com.example.finalprojectpam.ui.DetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailEventViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriSiswa: RepositoriEvent
) : ViewModel() {
    private val eventId: Int = checkNotNull(savedStateHandle[DetailsDestination.eventIdArg])
    val uiState: StateFlow<ItemDetailsUiState> =
        repositoriSiswa.getEventStream(eventId)
            .filterNotNull()
            .map {
                ItemDetailsUiState(detailEvent = it.toDetailEvent())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILIS),
                initialValue = ItemDetailsUiState()
            )

    suspend fun deleteItem(){
        repositoriSiswa.deleteEvent(uiState.value.detailEvent.toEvent())
    }

    companion object {
        private const val TIMEOUT_MILIS = 5_000L
    }
}

data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val detailEvent: DetailEvent = DetailEvent(),
)
package com.example.finalprojectpam.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.repositori.RepositoriEvent
import com.example.finalprojectpam.ui.ItemEditDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class EditEventViewModel(savedStateHandle: SavedStateHandle, private val repositoriEvent: RepositoriEvent) : ViewModel() {

    var eventUiState by mutableStateOf(UIStateEvent())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    init {
        viewModelScope.launch {
            eventUiState = repositoriEvent.getEventStream(itemId)
                .filterNotNull()
                .first()
                .toUiStateEvent(true)
        }
    }

    suspend fun updateEvent() {
        if (validateInput(eventUiState.detailEvent)) {
            repositoriEvent.updateEvent(eventUiState.detailEvent.toEvent())
        } else {
            println("Data tidak valid")
        }
    }

    fun updateUiState(detailEvent: DetailEvent) {
        eventUiState = UIStateEvent(detailEvent = detailEvent, isEntryValid = validateInput(detailEvent))
    }

    private fun validateInput(uiState: DetailEvent = eventUiState.detailEvent): Boolean {
        return with(uiState) {
            namaevent.isNotBlank() && alamatevent.isNotBlank() && penyelenggaraevent.isNotBlank() && deskripsievent.isNotBlank() && waktuevent.isNotBlank()
                    && tanggalevent.isNotBlank()
        }
    }
}
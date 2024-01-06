package com.example.finalprojectpam.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.finalprojectpam.database.Event
import com.example.finalprojectpam.repositori.RepositoriEvent

class EntryEventViewModel(private val repositoriEvent: RepositoriEvent): ViewModel(){

    var uiStateEvent by mutableStateOf(UIStateEvent())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailEvent = uiStateEvent.detailEvent): Boolean {
        return with(uiState){
            namaevent.isNotBlank() && alamatevent.isNotBlank() && penyelenggaraevent.isNotBlank() && deskripsievent.isNotBlank()
                    && waktuevent.isNotBlank() && tanggalevent.isNotBlank()
        }
    }

    fun updateUiState(detailEvent: DetailEvent){
        uiStateEvent = UIStateEvent(detailEvent = detailEvent, isEntryValid = validasiInput(detailEvent))
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun saveEvent(){
        if (validasiInput()) {
            repositoriEvent.insertEvent(uiStateEvent.detailEvent.toEvent())
        }
    }
}

data class UIStateEvent(
    val detailEvent: DetailEvent = DetailEvent(),
    val isEntryValid: Boolean = false
)

data class DetailEvent(
    val id: Int = 0,
    val namaevent: String = "",
    val alamatevent: String = "",
    val penyelenggaraevent: String = "",
    val deskripsievent: String = "",
    val waktuevent: String = "",
    val tanggalevent: String = "",
)

/* Fungsi untuk mengkonversi data input ke data dalam tabel sesuai jenis datanya */
fun DetailEvent.toEvent(): Event = Event(
    id = id,
    namaevent = namaevent,
    alamatevent = alamatevent,
    penyelenggaraevent = penyelenggaraevent,
    deskripsievent = deskripsievent,
    waktuevent = waktuevent,
    tanggalevent = tanggalevent,
)

fun Event.toUiStateEvent(isEntryValid: Boolean = false): UIStateEvent = UIStateEvent(
    detailEvent = this.toDetailEvent(),
    isEntryValid = isEntryValid
)

fun Event.toDetailEvent(): DetailEvent = DetailEvent(
    id = id,
    namaevent = namaevent,
    alamatevent = alamatevent,
    penyelenggaraevent = penyelenggaraevent,
    deskripsievent = deskripsievent,
    waktuevent = waktuevent,
    tanggalevent = tanggalevent
)
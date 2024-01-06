package com.example.finalprojectpam.model

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalprojectpam.AplikasiKonser

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiSiswa().container.repositoriEvent)
        }
        initializer {
            EntryEventViewModel(aplikasiSiswa().container.repositoriEvent)
        }
        initializer {
            DetailEventViewModel(createSavedStateHandle(),aplikasiSiswa().container.repositoriEvent)
        }
        initializer {
            EditEventViewModel(createSavedStateHandle(),aplikasiSiswa().container.repositoriEvent)
        }
    }
}

/**
 * Fungsi ekstensi query untuk objek [Application] dan mengembalikan sebuah instance dari
 * [AplikasiSiswa].
 */
fun CreationExtras.aplikasiSiswa(): AplikasiKonser =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiKonser)
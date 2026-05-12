package com.example.nammamela.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammamela.data.model.Seat
import com.example.nammamela.data.repository.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SeatMapViewModel(private val repository: AppRepository) : ViewModel() {

    fun getSeatsForPlay(playId: Int): StateFlow<List<Seat>> =
        repository.getSeatsForPlay(playId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun reserveSeat(seat: Seat) {
        viewModelScope.launch {
            repository.reserveSeat(seat)
        }
    }
}

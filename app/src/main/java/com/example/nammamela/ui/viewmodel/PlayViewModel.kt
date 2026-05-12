package com.example.nammamela.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammamela.data.model.CastMember
import com.example.nammamela.data.model.Play
import com.example.nammamela.data.repository.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PlayViewModel(private val repository: AppRepository) : ViewModel() {
    val play: StateFlow<Play?> = repository.play.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val allPlays: StateFlow<List<Play>> = repository.allPlays.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val castMembers: StateFlow<List<CastMember>> = repository.castMembers.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getCastForPlay(playId: Int): StateFlow<List<CastMember>> =
        repository.getCastMembersForPlay(playId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getPlayById(playId: Int): StateFlow<Play?> =
        repository.getPlayById(playId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}

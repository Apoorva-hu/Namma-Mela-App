package com.example.nammamela.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nammamela.data.model.FanComment
import com.example.nammamela.data.repository.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FanWallViewModel(private val repository: AppRepository) : ViewModel() {

    fun getCommentsForPlay(playId: Int): StateFlow<List<FanComment>> =
        repository.getFanCommentsForPlay(playId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addComment(playId: Int, userName: String, message: String) {
        viewModelScope.launch {
            repository.addComment(playId = playId, userName = userName, comment = message)
        }
    }
}

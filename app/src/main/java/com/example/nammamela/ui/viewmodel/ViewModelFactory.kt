package com.example.nammamela.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nammamela.data.repository.AppRepository

class ViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(SeatMapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SeatMapViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(FanWallViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FanWallViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(AIHelperViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AIHelperViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

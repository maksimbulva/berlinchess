package ru.maksimbulva.berlinchess.gameroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameRoomViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameRoomViewModel() as T
    }
}

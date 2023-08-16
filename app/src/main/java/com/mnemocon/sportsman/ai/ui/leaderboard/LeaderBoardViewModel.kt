package com.mnemocon.sportsman.ai.ui.leaderboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LeaderBoardViewModel(private val repository: LeaderboardRepository) : ViewModel() {
    private val _leaderboardEntries = MutableLiveData<List<LeaderboardEntry>>()
    val leaderboardEntries: LiveData<List<LeaderboardEntry>> = _leaderboardEntries

    init {
        refreshLeaderboard()
    }

    private fun refreshLeaderboard() {
        viewModelScope.launch {
            try {
                val response = repository.getLeaderboard()
                _leaderboardEntries.value = response.leaderboard
            } catch (e: Exception) {
                // Обработка ошибки, например, запись в лог
                Log.e("LeaderBoardViewModel", "Error refreshing leaderboard", e)
            }
        }
    }
}

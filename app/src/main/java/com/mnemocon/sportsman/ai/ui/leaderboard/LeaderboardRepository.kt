package com.mnemocon.sportsman.ai.ui.leaderboard

class LeaderboardRepository(private val api: LeaderboardApi) {
    suspend fun getLeaderboard(): LeaderboardResponse {
        return api.getLeaderboard()
    }
}

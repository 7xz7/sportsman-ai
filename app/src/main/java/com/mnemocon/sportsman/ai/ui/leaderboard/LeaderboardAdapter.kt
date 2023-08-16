package com.mnemocon.sportsman.ai.ui.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemocon.sportsman.ai.R
import retrofit2.http.GET

data class LeaderboardResponse(
    val leaderboard: List<LeaderboardEntry>
)

// Модель данных для элемента leaderboard
data class LeaderboardEntry(
    val name: String,
    val score: Int
)

// Интерфейс для Retrofit
interface LeaderboardApi {
    @GET("api/aisport/get_leaderboard.php")
    suspend fun getLeaderboard(): LeaderboardResponse
}

class LeaderboardAdapter : ListAdapter<LeaderboardEntry, LeaderboardAdapter.ViewHolder>(LeaderboardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = getItem(position)
        holder.bind(entry)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val scoreTextView: TextView = itemView.findViewById(R.id.scoreTextView)

        fun bind(entry: LeaderboardEntry) {
            nameTextView.text = entry.name
            scoreTextView.text = entry.score.toString()
        }
    }

    class LeaderboardDiffCallback : DiffUtil.ItemCallback<LeaderboardEntry>() {
        override fun areItemsTheSame(oldItem: LeaderboardEntry, newItem: LeaderboardEntry): Boolean {
            return oldItem.name == newItem.name // Предполагается, что имя является уникальным идентификатором
        }

        override fun areContentsTheSame(oldItem: LeaderboardEntry, newItem: LeaderboardEntry): Boolean {
            return oldItem == newItem
        }
    }
}

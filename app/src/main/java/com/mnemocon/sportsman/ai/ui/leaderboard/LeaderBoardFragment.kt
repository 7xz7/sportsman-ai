package com.mnemocon.sportsman.ai.ui.leaderboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mnemocon.sportsman.ai.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LeaderBoardViewModelFactory(private val repository: LeaderboardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderBoardViewModel::class.java)) {
            return LeaderBoardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LeaderBoardFragment : Fragment() {
    private lateinit var viewModel: LeaderBoardViewModel
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_leader_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Создание API и репозитория
        val leaderboardApi = Retrofit.Builder()
            .baseUrl("https://meditation.mnemocon.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LeaderboardApi::class.java)
        val repository = LeaderboardRepository(leaderboardApi)

        // Создание фабрики ViewModel
        val viewModelFactory = LeaderBoardViewModelFactory(repository)

        // Получение ViewModel
        viewModel = ViewModelProvider(this, viewModelFactory)[LeaderBoardViewModel::class.java]

        val recyclerView = view?.findViewById<RecyclerView>(R.id.leaderboardRecyclerView)
        setupRecyclerView(recyclerView)
        observeData()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView?) {
        adapter = LeaderboardAdapter()
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
    }

    private fun observeData() {
        viewModel.leaderboardEntries.observe(viewLifecycleOwner, { entries ->
            adapter.submitList(entries)
        })
    }
}

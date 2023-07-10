package com.mnemocon.sportsman.ai

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemocon.sportsman.ai.data.Dao

class SeePastCountsViewModelFactory(private val dataSource: Dao,
                                    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SeePastCountsViewModel::class.java)) {
            return SeePastCountsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
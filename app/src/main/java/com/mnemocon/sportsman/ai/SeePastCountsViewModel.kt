package com.mnemocon.sportsman.ai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mnemocon.sportsman.ai.data.Dao
import com.mnemocon.sportsman.ai.data.Table
import kotlinx.coroutines.launch

class SeePastCountsViewModel(val database: Dao,
                             application: Application
) : AndroidViewModel(application) {

    lateinit var data: LiveData<List<Table>>

    init {
        viewModelScope.launch {
            data = database.getTable()
        }
    }



}
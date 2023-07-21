package com.mnemocon.sportsman.ai

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mnemocon.sportsman.ai.data.Dao
import com.mnemocon.sportsman.ai.data.Table
import kotlinx.coroutines.launch

// ViewModel для просмотра прошлых записей
class SeePastCountsViewModel(
    val database: Dao, // Источник данных
    application: Application // Приложение
) : AndroidViewModel(application) {

    // Данные для отображения
    lateinit var data: LiveData<List<Table>>

    init {
        // Получение данных при инициализации ViewModel
        viewModelScope.launch {
            data = database.getTable()
        }
    }
}

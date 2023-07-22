package com.mnemocon.sportsman.ai

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mnemocon.sportsman.ai.data.Dao

// Фабрика для создания ViewModel с зависимостями
class SeePastCountsViewModelFactory(
    private val dataSource: Dao, // Источник данных
    private val application: Application // Приложение
) : ViewModelProvider.Factory {

    // Создание ViewModel
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Проверка соответствия класса ViewModel
        if (modelClass.isAssignableFrom(SeePastCountsViewModel::class.java)) {
            return SeePastCountsViewModel(dataSource, application) as T
        }
        // Если ViewModel не соответствует, бросить исключение
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

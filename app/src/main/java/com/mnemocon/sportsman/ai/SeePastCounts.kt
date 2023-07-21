package com.mnemocon.sportsman.ai

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mnemocon.sportsman.ai.data.AppDatabase
import com.mnemocon.sportsman.ai.data.Datum
import com.mnemocon.sportsman.ai.databinding.FragmentSeePastCountsBinding

// Фрагмент для просмотра прошлых записей
class SeePastCounts : Fragment() {
    private lateinit var binding: FragmentSeePastCountsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Установка ориентации экрана в портретный режим
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Инициализация привязки
        binding = FragmentSeePastCountsBinding.inflate(layoutInflater, container, false)

        val application = requireNotNull(this.activity).application

        // Создание экземпляра фабрики ViewModel
        val dataSource = AppDatabase.getInstance(application).dao
        val viewModelFactory = SeePastCountsViewModelFactory(dataSource, application)

        // Получение ссылки на ViewModel, связанную с этим фрагментом
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SeePastCountsViewModel::class.java)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        val adapter = SeePastCountsAdapter()

        binding.cardList.adapter = adapter

        // Обработчик клика для кнопки "Назад"
        binding.imageButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Наблюдение за данными и их обновление в адаптере
        viewModel.data.observe(viewLifecycleOwner, Observer { items ->
            val hmm = ArrayList<Datum.Card>()
            for(item in items) {
                hmm.add(Datum.Card(item.id, item.dateTime, item.duration, item.pushups, item.squats))
            }
            adapter.submitList(hmm)
        })
        return binding.root
    }

    // При отсоединении фрагмента возвращаем стандартную ориентацию экрана
    override fun onDetach() {
        super.onDetach()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}

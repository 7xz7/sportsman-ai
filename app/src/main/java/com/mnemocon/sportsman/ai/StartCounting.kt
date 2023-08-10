package com.mnemocon.sportsman.ai

// Импорт необходимых библиотек и компонентов
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mnemocon.sportsman.ai.databinding.FragmentStartCountingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.async

class StartCounting : Fragment() {

    // Объявление переменной для привязки
    private lateinit var binding: FragmentStartCountingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Установка ориентации портретного режима
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Инициализация привязки
        binding = FragmentStartCountingBinding.inflate(layoutInflater, container, false)

        // Обработчик клика для карточки "Только отжимания"
        binding.cardPushupsOnly.setOnClickListener{
            val direction =
                StartCountingDirections.actionStartCountingToCamera(true, false)
            findNavController().navigate(direction)
        }

        // Обработчик клика для карточки "Только приседания"
        binding.cardSquatsOnly.setOnClickListener{
            val direction =
                StartCountingDirections.actionStartCountingToCamera(false, true)
            findNavController().navigate(direction)
        }

        // Обработчик клика для карточки "Оба вида упражнений"
        binding.cardBoth.setOnClickListener{
            val direction =
                StartCountingDirections.actionStartCountingToCamera(true, true)
            findNavController().navigate(direction)
        }

        // Обработчик клика для кнопки "Назад"
        binding.imageButton2.setOnClickListener {
            findNavController().navigateUp()
        }

        // Возвращение корневого представления
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Возвращение ориентации экрана к стандартной при уничтожении представления
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}

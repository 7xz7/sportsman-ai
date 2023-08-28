package com.mnemocon.sportsman.ai.ui.user_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mnemocon.sportsman.ai.MainActivityViewModel
import com.mnemocon.sportsman.ai.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        // Настройка ViewModel
        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        // Наблюдение за изменениями имени пользователя
        viewModel.userName.observe(viewLifecycleOwner) { newName ->
            binding.userNameTextView.text = newName
        }

        // Установка слушателя на кнопку редактирования имени
        binding.editNameButton.setOnClickListener {
            val newName = binding.userNameEditText.text.toString()
            viewModel.updateFirebaseUserName(newName) // Обновление имени в Firebase и ViewModel
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
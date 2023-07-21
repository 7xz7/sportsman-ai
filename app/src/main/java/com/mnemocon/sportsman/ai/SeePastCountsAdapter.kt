package com.mnemocon.sportsman.ai

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mnemocon.sportsman.ai.data.Datum
import com.mnemocon.sportsman.ai.databinding.CardListBinding

// Адаптер для отображения списка прошлых записей
class SeePastCountsAdapter : ListAdapter<Datum.Card, RecyclerView.ViewHolder>(CardDiffCallback()) {

    // Создание ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CardViewHolder(
            CardListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Привязка данных к ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as CardViewHolder).bind(item)
    }

    // ViewHolder для элемента списка
    class CardViewHolder(
        private val binding: CardListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Datum.Card) {
            binding.apply {
                card = item
                executePendingBindings()
            }
        }
    }
}

// Callback для определения изменений в списке
private class CardDiffCallback : DiffUtil.ItemCallback<Datum.Card>() {

    // Сравнение элементов по ID
    override fun areItemsTheSame(oldItem: Datum.Card, newItem: Datum.Card): Boolean {
        return oldItem.id == newItem.id
    }

    // Сравнение содержимого элементов
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Datum.Card, newItem: Datum.Card): Boolean {
        return oldItem == newItem
    }
}

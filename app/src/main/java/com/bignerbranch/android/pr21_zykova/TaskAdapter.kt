package com.bignerbranch.android.pr21_zykova

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerbranch.android.pr21_zykova.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(
    private var tasks: MutableList<Task>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(newTasks: MutableList<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        private val shortDateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())

        @SuppressLint("SetTextI18n")
        fun bind(task: Task) {
            binding.textTitle.text = task.title
            binding.textDescription.text = task.description
            binding.textCategory.text = task.category

            //отображаем дату создания с проверкой на null
            if (task.dateCreated != null) {
                binding.textCreatedDate.text = dateFormat.format(task.dateCreated)
            } else {
                binding.textCreatedDate.text = "Не указана"
            }

            //отображаем дату выполнения (если она установлена)
            task.dueDate?.let { dueDate ->
                binding.textDueDate.text = shortDateFormat.format(dueDate)
                binding.textDueDate.visibility = android.view.View.VISIBLE

                //подсвечиваем просроченные задачи
                if (dueDate.before(Date())) {
                    binding.textDueDate.setTextColor(android.graphics.Color.RED)
                    binding.textDueDate.setCompoundDrawablesWithIntrinsicBounds(
                        android.R.drawable.ic_dialog_alert,
                        0, 0, 0
                    )
                }
            } ?: run {
                binding.textDueDate.visibility = android.view.View.GONE
            }
        }
    }
}
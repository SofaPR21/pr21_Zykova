package com.bignerbranch.android.pr21_zykova

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bignerbranch.android.pr21_zykova.databinding.ActivityAddTaskBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private val gson = Gson()
    private val prefs by lazy { getSharedPreferences("user_prefs", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val title = binding.editTitle.text.toString().trim()
            val desc = binding.editDescription.text.toString().trim()

            if (title.isEmpty()) {
                binding.editTitle.error = "Введите название"
                return@setOnClickListener
            }

            val newTask = Task(title = title, description = desc)
            saveTask(newTask)
            finish()
        }

        binding.btnCancel.setOnClickListener { finish() }
    }

    private fun saveTask(task: Task) {
        val json = prefs.getString("tasks", "[]") ?: "[]"
        val type = object : TypeToken<MutableList<Task>>() {}.type
        val tasks: MutableList<Task> = gson.fromJson(json, type)
        tasks.add(task)

        prefs.edit().putString("tasks", gson.toJson(tasks)).apply()
        Toast.makeText(this, "Задача добавлена", Toast.LENGTH_SHORT).show()
    }
}
package com.bignerbranch.android.pr21_zykova

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bignerbranch.android.pr21_zykova.databinding.ActivityAddTaskBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private val gson = Gson()
    private val prefs by lazy { getSharedPreferences("user_prefs", MODE_PRIVATE) }

    private var selectedDueDate: Date? = null
    private val categories = listOf("Общее", "Работа", "Учеба", "Личное", "Здоровье", "Финансы")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupDatePicker()

        binding.btnSave.setOnClickListener {
            val title = binding.editTitle.text.toString().trim()
            val desc = binding.editDescription.text.toString().trim()
            val category = binding.spinnerCategory.selectedItem.toString()

            if (title.isEmpty()) {
                binding.editTitle.error = "Введите название"
                return@setOnClickListener
            }

            //создаем задачу с текущим временем создания
            val newTask = Task(
                title = title,
                description = desc,
                category = category,
                dateCreated = Date(), //текущее время
                dueDate = selectedDueDate
            )

            saveTask(newTask)
            finish()
        }

        binding.btnCancel.setOnClickListener { finish() }
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            R.layout.custom_spinner_item,
            categories
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()

        binding.btnDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(year, month, dayOfMonth)
                    selectedDueDate = selectedCalendar.time

                    //форматируем дату для отображения
                    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    binding.btnDate.text = dateFormat.format(selectedDueDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }
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
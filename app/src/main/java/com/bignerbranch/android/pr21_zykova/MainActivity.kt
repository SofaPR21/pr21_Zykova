package com.bignerbranch.android.pr21_zykova

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewWelcome = findViewById<TextView>(R.id.textViewWelcome)
        val buttonLogout = findViewById<Button>(R.id.buttonLogout)
        val buttonTasks = findViewById<Button>(R.id.buttonTasks)

        //получаем логин
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val login = prefs.getString("login", "Пользователь")
        textViewWelcome.text = "Добро пожаловать, $login!"

        //переход к списку задач
        buttonTasks.setOnClickListener {
            startActivity(Intent(this, TaskListActivity::class.java))
        }

        buttonLogout.setOnClickListener {
            //возвращаемся на экран входа
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
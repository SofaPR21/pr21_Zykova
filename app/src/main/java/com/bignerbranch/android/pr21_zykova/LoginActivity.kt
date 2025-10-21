package com.bignerbranch.android.pr21_zykova

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextLogin: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity_main)

        editTextLogin = findViewById(R.id.editTextLogin)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        buttonLogin.setOnClickListener {
            val login = editTextLogin.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6 ) {
                Toast.makeText(this, "парол должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //проверяем, есть ли уже логин и пароль
            val savedLogin = prefs.getString("login", null)
            val savedPassword = prefs.getString("password", null)

            if (savedLogin == null || savedPassword == null) {
                with(prefs.edit()) {
                    putString("login", login)
                    putString("password", password)
                    apply()
                }
                goToMainActivity() //переход на главную
            }
            else {
                if (login == savedLogin && password == savedPassword) {
                    goToMainActivity() //переход на главную
                }
                else {
                    Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
package com.bignerbranch.android.pr21_zykova

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerbranch.android.pr21_zykova.databinding.ActivityTaskListHostBinding

class TaskListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTaskListHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TaskListFragment())
                .commit()
        }
    }
}
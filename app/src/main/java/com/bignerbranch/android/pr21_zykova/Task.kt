package com.bignerbranch.android.pr21_zykova

data class Task(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val description: String = ""
)
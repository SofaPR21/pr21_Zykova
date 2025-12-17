package com.bignerbranch.android.pr21_zykova

import java.util.Date

data class Task(
    val id: Long = System.currentTimeMillis(),
    val title: String, //заголовок
    val description: String = "", //описание
    val category: String = "Общее", //категория
    val dateCreated: Date? = Date(), //текущее время создания
    val dueDate: Date? = null //дата выполнения
)
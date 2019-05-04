package com.prospect.core.domain.task

data class Task(
        val id: String,
        val title: String,
        val description: String,
        val costTime: CostTime?
)
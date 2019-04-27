package com.prospect.core.domain.type

import java.lang.IllegalArgumentException

data class Priority(val value: Int) : Comparable<Priority> {

    companion object {
        fun of(newValue: Int) = Priority(newValue)
    }

    init {
        if (value < 1) throw IllegalArgumentException("Priorityは1以上の値である必要があります")
    }

    fun down(): Priority = of(value + 1)

    fun up(): Priority = of(value - 1)

    override fun compareTo(other: Priority): Int {
        return when {
            other.value < value -> -1
            other.value > value -> 1
            else -> 0
        }
    }

}

package com.prospect.core.domain.task

import java.time.Duration

class CostTime(hours: Long, minutes: Long) {

    val duration: Duration = Duration.ofHours(hours).plus(Duration.ofMinutes(minutes))

    init {
        if (!validateHoursAndMinutes(hours, minutes)) throw IllegalArgumentException("CostTimeの値が負です")
    }

    private fun validateHoursAndMinutes(hours: Long, minutes: Long): Boolean = !(hours < 0 || minutes < 0)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CostTime

        if (duration != other.duration) return false

        return true
    }

    override fun hashCode(): Int {
        return duration.hashCode()
    }

}

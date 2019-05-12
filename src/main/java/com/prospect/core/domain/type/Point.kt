package com.prospect.core.domain.type

data class Point(val value: Int) : Comparable<Point> {

    companion object {
        fun of(value: Int): Point = Point(value)
    }

    init {
        if (value < 0) throw IllegalArgumentException("Pointは0以上の値である必要があります")
    }

    override fun compareTo(other: Point): Int {
        return when {
            other.value > value -> -1
            other.value < value -> 1
            else -> 0
        }
    }

}
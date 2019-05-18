package com.prospect.core.domain.feature

data class Order(val value: Int) : Comparable<Order> {

    companion object {
        fun of(value: Int): Order = Order(value)
    }

    init {
        if (value < 0) throw IllegalArgumentException("Orderは0未満の値を持つことができません")
    }

    override fun compareTo(other: Order): Int {
        return when {
            value > other.value -> -1
            value < other.value -> 1
            else -> 0
        }
    }

    fun down(): Order = Order(value + 1)

    fun up(): Order = Order(value - 1)

}

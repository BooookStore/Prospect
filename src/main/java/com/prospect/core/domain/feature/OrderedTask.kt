package com.prospect.core.domain.feature

data class OrderedTask(
        val taskId: String,
        val order: Order
) {

    fun downOrder() = OrderedTask(taskId, order.down())

    fun upOrder() = OrderedTask(taskId, order.up())

}
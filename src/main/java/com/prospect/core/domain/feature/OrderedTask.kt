package com.prospect.core.domain.feature

import com.prospect.core.domain.type.Order

data class OrderedTask(
        val taskId: String,
        val order: Order
) {

    fun downOrder() = OrderedTask(taskId, order.down())

    fun upOrder() = OrderedTask(taskId, order.up())

}
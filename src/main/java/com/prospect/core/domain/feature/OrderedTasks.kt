package com.prospect.core.domain.feature

data class OrderedTasks(val tasks: List<OrderedTask> = listOf()) {

    companion object {
        fun of(lamb: () -> List<OrderedTask>): OrderedTasks = OrderedTasks(lamb())
    }

    fun add(orderedTask: OrderedTask): OrderedTasks = of {
        if (!validOrder(orderedTask.order)) throw IllegalArgumentException("OrderedTaskのOrderが不正です")
        if (contain(orderedTask)) throw IllegalArgumentException("既に存在するOrderedTaskです")

        tasks.divideByOrder(orderedTask.order).let { (over, under) ->
            listOf(
                    *over.toTypedArray(),
                    *under.downOrder().toTypedArray()
            ).add(orderedTask)
        }
    }

    fun remove(orderedTask: OrderedTask): OrderedTasks = of {
        if (!contain(orderedTask)) throw IllegalArgumentException("存在しないOrderedTaskです")

        tasks.divideByOrder(orderedTask.order).let { (over, under) ->
            listOf(
                    *over.toTypedArray(),
                    *under.remove(orderedTask).upOrder().toTypedArray()
            )
        }
    }

    private fun validOrder(order: Order): Boolean {
        val lastTask = tasks.sortedBy { it.order }.firstOrNull()
        return if (lastTask == null) {
            order == Order.of(1)
        } else {
            order >= lastTask.order.down()
        }
    }

    fun size(): Int = tasks.size

    fun isEmpty(): Boolean = tasks.isEmpty()

    private fun contain(orderedTask: OrderedTask): Boolean = tasks.contains(orderedTask)

}

private fun List<OrderedTask>.divideByOrder(order: Order): Pair<List<OrderedTask>, List<OrderedTask>> {
    val under = filter { it.order <= order }
    val over = filter { it.order > order }

    return Pair(over, under)
}

private fun List<OrderedTask>.downOrder() = map { it.downOrder() }

private fun List<OrderedTask>.upOrder() = map { it.upOrder() }

private fun List<OrderedTask>.add(orderedTask: OrderedTask): List<OrderedTask> {
    val list = mutableListOf<OrderedTask>()
    list.addAll(this)
    list.add(orderedTask)
    return list
}

private fun List<OrderedTask>.remove(orderedTask: OrderedTask): List<OrderedTask> {
    val list = mutableListOf<OrderedTask>()
    list.addAll(this)
    list.remove(orderedTask)
    return list
}

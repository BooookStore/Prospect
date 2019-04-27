package com.prospect.core.domain.project

import com.prospect.core.domain.type.Priority

data class ProductBacklogItems(private val items: Items = listOf()) {

    companion object {
        fun of(lamb: () -> Items): ProductBacklogItems = ProductBacklogItems(lamb())
    }

    fun add(productBacklogItem: ProductBacklogItem): ProductBacklogItems = of {
        if (!allowancePriorityRange(productBacklogItem)) throw IllegalArgumentException("追加するProductBacklogItemの優先度が不正です")
        if (!notContainsFeatureId(productBacklogItem)) throw IllegalArgumentException("既に同じFeatureIdを持つProductBacklogItemが存在します")

        items.divideByPriority(productBacklogItem.priority).let { (over, under) ->
            listOf(
                    *over.toTypedArray(),
                    *under.downPriority().toTypedArray()
            ).add(productBacklogItem)
        }
    }

    fun remove(productBacklogItem: ProductBacklogItem): ProductBacklogItems = of {
        if (!contain(productBacklogItem)) throw IllegalArgumentException("存在しないProductBacklogItemです")

        items.divideByPriority(productBacklogItem.priority).let { (over, under) ->
            listOf(
                    *over.toTypedArray(),
                    *under.remove(productBacklogItem).upPriority().toTypedArray()
            )
        }
    }

    fun isEmpty(): Boolean = items.isEmpty()

    fun findByPriority(priority: Priority): ProductBacklogItem? = items.findByPriority(priority)

    fun size(): Int = items.size

    private fun allowancePriorityRange(productBacklogItem: ProductBacklogItem): Boolean {
        val lowestProductBacklog = items.lastPriority()
        return if (lowestProductBacklog == null) {
            productBacklogItem.priority == Priority.of(1)
        } else {
            val allowanceLowestPriority = lowestProductBacklog.priority.down()
            productBacklogItem.priority >= allowanceLowestPriority
        }
    }

    private fun notContainsFeatureId(productBacklogItem: ProductBacklogItem): Boolean =
            !items.containsByFeatureId(productBacklogItem.featureId)

    private fun contain(productBacklogItem: ProductBacklogItem): Boolean =
            items.contains(productBacklogItem)
}

private typealias Items = List<ProductBacklogItem>

private fun Items.divideByPriority(priority: Priority): Pair<Items, Items> {
    val under = filter { it.priority <= priority }
    val over = filter { it.priority > priority }
    return Pair(over, under)
}

private fun Items.downPriority(): Items = map { it.downPriority() }

private fun Items.upPriority(): Items = map { it.upPriority() }

private fun Items.add(productBacklogItem: ProductBacklogItem): Items {
    val mutableList = mutableListOf<ProductBacklogItem>()
    mutableList.addAll(this)
    mutableList.add(productBacklogItem)
    return mutableList
}

private fun Items.remove(productBacklogItem: ProductBacklogItem): Items {
    val mutableList = mutableListOf<ProductBacklogItem>()
    mutableList.addAll(this)
    mutableList.remove(productBacklogItem)
    return mutableList
}

private fun Items.findByPriority(priority: Priority): ProductBacklogItem? = firstOrNull { it.priority == priority }

private fun Items.lastPriority(): ProductBacklogItem? = sortedBy { it.priority }.firstOrNull()

private fun Items.containsByFeatureId(featureId: String): Boolean = any { it.featureId == featureId }
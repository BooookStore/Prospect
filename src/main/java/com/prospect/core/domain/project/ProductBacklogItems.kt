package com.prospect.core.domain.project

import com.prospect.core.domain.type.Priority

data class ProductBacklogItems(val items: List<ProductBacklogItem> = listOf()) {

    companion object {
        fun of(lamb: () -> List<ProductBacklogItem>): ProductBacklogItems = ProductBacklogItems(lamb())
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

private fun List<ProductBacklogItem>.divideByPriority(priority: Priority): Pair<List<ProductBacklogItem>, List<ProductBacklogItem>> {
    val under = filter { it.priority <= priority }
    val over = filter { it.priority > priority }
    return Pair(over, under)
}

private fun List<ProductBacklogItem>.downPriority(): List<ProductBacklogItem> = map { it.downPriority() }

private fun List<ProductBacklogItem>.upPriority(): List<ProductBacklogItem> = map { it.upPriority() }

private fun List<ProductBacklogItem>.add(productBacklogItem: ProductBacklogItem): List<ProductBacklogItem> {
    val mutableList = mutableListOf<ProductBacklogItem>()
    mutableList.addAll(this)
    mutableList.add(productBacklogItem)
    return mutableList
}

private fun List<ProductBacklogItem>.remove(productBacklogItem: ProductBacklogItem): List<ProductBacklogItem> {
    val mutableList = mutableListOf<ProductBacklogItem>()
    mutableList.addAll(this)
    mutableList.remove(productBacklogItem)
    return mutableList
}

private fun List<ProductBacklogItem>.findByPriority(priority: Priority): ProductBacklogItem? = firstOrNull { it.priority == priority }

private fun List<ProductBacklogItem>.lastPriority(): ProductBacklogItem? = sortedBy { it.priority }.firstOrNull()

private fun List<ProductBacklogItem>.containsByFeatureId(featureId: String): Boolean = any { it.featureId == featureId }
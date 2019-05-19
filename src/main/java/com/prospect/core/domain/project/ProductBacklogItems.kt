package com.prospect.core.domain.project

import com.prospect.core.domain.type.OrderedCollection
import com.prospect.core.domain.type.Priority

data class ProductBacklogItems(val orderedItems: OrderedCollection<ProductBacklogItem> = OrderedCollection.of(listOf())) {

    companion object {
        fun of(lamb: () -> OrderedCollection<ProductBacklogItem>): ProductBacklogItems = ProductBacklogItems(lamb())
    }

    fun add(productBacklogItem: ProductBacklogItem): ProductBacklogItems = of {
        if (!allowancePriorityRange(productBacklogItem)) throw NotAllowanceProductBacklogItemPriorityException()
        if (!notContainsFeatureId(productBacklogItem)) throw IllegalArgumentException("既に同じFeatureIdを持つProductBacklogItemが存在します")

        val (over, under) = orderedItems.divideByPriority { it.priority <= productBacklogItem.priority }
        under.downOrder { it.downPriority() }.addAll(over).add(productBacklogItem)
    }

    fun remove(productBacklogItem: ProductBacklogItem): ProductBacklogItems = of {
        if (!contain(productBacklogItem)) throw IllegalArgumentException("存在しないProductBacklogItemです")

        val (over, under) = orderedItems.divideByPriority { it.priority <= productBacklogItem.priority }
        under.remove(productBacklogItem).upOrder { it.upPriority() }.addAll(over)
    }

    fun findByPriority(priority: Priority): ProductBacklogItem? = orderedItems.elements.firstOrNull { it.priority == priority }

    fun findLowestProductBacklogItem(): ProductBacklogItem? = orderedItems.elements.sortedBy { it.priority }.firstOrNull()

    fun isEmpty(): Boolean = orderedItems.elements.isEmpty()

    fun size(): Int = orderedItems.elements.size

    private fun allowancePriorityRange(productBacklogItem: ProductBacklogItem): Boolean {
        val lowestProductBacklog = findLowestProductBacklogItem()
        return if (lowestProductBacklog == null) {
            productBacklogItem.priority == Priority.of(1)
        } else {
            val allowanceLowestPriority = lowestProductBacklog.downPriority().priority
            productBacklogItem.priority >= allowanceLowestPriority
        }
    }

    private fun notContainsFeatureId(productBacklogItem: ProductBacklogItem): Boolean =
            !orderedItems.elements.any { it.featureId == productBacklogItem.featureId }

    private fun contain(productBacklogItem: ProductBacklogItem): Boolean =
            orderedItems.elements.contains(productBacklogItem)

}

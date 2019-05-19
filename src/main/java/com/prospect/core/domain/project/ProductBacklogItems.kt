package com.prospect.core.domain.project

import com.prospect.core.domain.type.OrderedCollection
import com.prospect.core.domain.type.Priority

data class ProductBacklogItems(val items: List<ProductBacklogItem> = listOf()) {

    private val orderedItems: OrderedCollection<ProductBacklogItem> = OrderedCollection.of(items)

    companion object {
        fun of(lamb: () -> List<ProductBacklogItem>): ProductBacklogItems = ProductBacklogItems(lamb())
    }

    fun add(productBacklogItem: ProductBacklogItem): ProductBacklogItems = of {
        if (!allowancePriorityRange(productBacklogItem)) throw NotAllowanceProductBacklogItemPriorityException()
        if (!notContainsFeatureId(productBacklogItem)) throw IllegalArgumentException("既に同じFeatureIdを持つProductBacklogItemが存在します")

        val (over, under) = orderedItems.divideByPriority { it.priority <= productBacklogItem.priority }
        val newOrderedItems = under.downOrder { it.downPriority() }.addAll(over).add(productBacklogItem)

        newOrderedItems.elements
    }

    fun remove(productBacklogItem: ProductBacklogItem): ProductBacklogItems = of {
        if (!contain(productBacklogItem)) throw IllegalArgumentException("存在しないProductBacklogItemです")

        val (over, under) = orderedItems.divideByPriority { it.priority <= productBacklogItem.priority }
        val newOrderedItems = under.remove(productBacklogItem).upOrder { it.upPriority() }.addAll(over)

        newOrderedItems.elements
    }

    fun isEmpty(): Boolean = items.isEmpty()

    fun findByPriority(priority: Priority): ProductBacklogItem? = items.firstOrNull { it.priority == priority }

    fun findLowestProductBacklogItem(): ProductBacklogItem? = items.sortedBy { it.priority }.firstOrNull()

    fun size(): Int = items.size

    private fun allowancePriorityRange(productBacklogItem: ProductBacklogItem): Boolean {
        val lowestProductBacklog = items.sortedBy { it.priority }.firstOrNull()
        return if (lowestProductBacklog == null) {
            productBacklogItem.priority == Priority.of(1)
        } else {
            val allowanceLowestPriority = lowestProductBacklog.priority.down()
            productBacklogItem.priority >= allowanceLowestPriority
        }
    }

    private fun notContainsFeatureId(productBacklogItem: ProductBacklogItem): Boolean =
            !items.any { it.featureId == productBacklogItem.featureId }

    private fun contain(productBacklogItem: ProductBacklogItem): Boolean =
            items.contains(productBacklogItem)

}

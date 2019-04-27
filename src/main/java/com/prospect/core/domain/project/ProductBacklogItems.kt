package com.prospect.core.domain.project

import com.prospect.core.domain.type.Priority

data class ProductBacklogItems(private val items: Items = listOf()) {

    companion object {
        fun of(lamb: () -> Items): ProductBacklogItems = ProductBacklogItems(lamb())
    }

    fun add(productBacklogItem: ProductBacklogItem): ProductBacklogItems = of {
        items.underPriority(productBacklogItem)
                .downPriority()
                .add(productBacklogItem)
    }

    fun remove(productBacklogItem: ProductBacklogItem): ProductBacklogItems = of {
        items.underPriority(productBacklogItem)
                .remove(productBacklogItem)
                .upPriority()
    }

    fun isEmpty(): Boolean = items.isEmpty()

    fun findByPriority(priority: Priority): ProductBacklogItem? = items.findByPriority(priority)

    fun size(): Int = items.size

}

private typealias Items = List<ProductBacklogItem>

private fun Items.underPriority(productBacklogItem: ProductBacklogItem): Items = filter { it.priority <= productBacklogItem.priority }

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

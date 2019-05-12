package com.prospect.core.domain.project

import com.prospect.core.domain.type.Priority

data class Project(
        val id: String,
        var name: String,
        var iceBoxItems: IceBoxItems = IceBoxItems(),
        var productBacklogItems: ProductBacklogItems = ProductBacklogItems()
) {

    fun addIceBoxItem(featureId: String) {
        iceBoxItems = iceBoxItems.add(featureId)
    }

    fun removeIceBoxItem(featureId: String) {
        iceBoxItems = iceBoxItems.remove(featureId)
    }

    fun addProductBacklogItem(productBacklogItem: ProductBacklogItem) {
        productBacklogItems = productBacklogItems.add(productBacklogItem)
    }

    fun findLowestProductBacklogItem(): ProductBacklogItem? {
        return productBacklogItems.findLowestProductBacklogItem()
    }

    fun removeProductBacklogItem(productBacklogItem: ProductBacklogItem) {
        productBacklogItems = productBacklogItems.remove(productBacklogItem)
    }

    fun moveIceBoxItemToProductBacklogItem(featureId: String, priority: Priority) {
        iceBoxItems.remove(featureId)
        productBacklogItems.add(ProductBacklogItem(featureId, priority))
    }

    fun moveProductBacklogItemToIceBoxItem(productBacklogItem: ProductBacklogItem) {
        productBacklogItems.remove(productBacklogItem)
        iceBoxItems.add(productBacklogItem.featureId)
    }

}
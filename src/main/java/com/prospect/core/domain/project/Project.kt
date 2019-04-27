package com.prospect.core.domain.project

class Project(
        val id: String,
        private var iceBoxItems: Set<String> = setOf(),
        private var productBacklogItems: ProductBacklogItems = ProductBacklogItems()
) {

    fun addIceBoxItem(featureId: String) {
        iceBoxItems += featureId
    }

    fun removeIceBoxItem(featureId: String) {
        iceBoxItems -= featureId
    }

    fun addProductBacklogItem(productBacklogItem: ProductBacklogItem) {
        productBacklogItems = productBacklogItems.add(productBacklogItem)
    }

    fun removeProductBacklogItem(productBacklogItem: ProductBacklogItem) {
        productBacklogItems = productBacklogItems.remove(productBacklogItem)
    }

}
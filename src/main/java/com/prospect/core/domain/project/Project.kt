package com.prospect.core.domain.project

class Project(
        val id: String,
        var name: String,
        private var iceBoxItems: IceBoxItems = IceBoxItems(),
        private var productBacklogItems: ProductBacklogItems = ProductBacklogItems()
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

    fun removeProductBacklogItem(productBacklogItem: ProductBacklogItem) {
        productBacklogItems = productBacklogItems.remove(productBacklogItem)
    }

}
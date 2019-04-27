package com.prospect.core.domain.project

import com.prospect.core.domain.feature.Feature

class Project(
        val id: String,
        private var iceBoxItems: Set<Feature> = setOf(),
        private var productBacklogItems: ProductBacklogItems = ProductBacklogItems()
) {

    fun addIceBoxItem(feature: Feature) {
        iceBoxItems += feature
    }

    fun removeIceBoxItem(feature: Feature) {
        iceBoxItems -= feature
    }

    fun addProductBacklogItem(productBacklogItem: ProductBacklogItem) {
        productBacklogItems = productBacklogItems.add(productBacklogItem)
    }

    fun removeProductBacklogItem(productBacklogItem: ProductBacklogItem) {
        productBacklogItems = productBacklogItems.remove(productBacklogItem)
    }

}
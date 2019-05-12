package com.prospect.core.domain.project

import com.prospect.core.domain.type.Priority

data class ProductBacklogItem(
        val featureId: String,
        val priority: Priority
) {

    fun downPriority(): ProductBacklogItem = ProductBacklogItem(featureId, priority.down())

    fun upPriority(): ProductBacklogItem = ProductBacklogItem(featureId, priority.up())

    fun changePriority(priority: Priority) = ProductBacklogItem(featureId, priority)

}
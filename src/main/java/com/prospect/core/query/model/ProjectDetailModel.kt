package com.prospect.core.query.model

data class ProjectDetailModel(
        val id: String,
        val name: String,
        val iceBoxItems: List<String>,
        val productBacklogItems: List<ProductBacklogItemModel>
)

data class ProductBacklogItemModel(
        val id: String,
        val priority: Int
)

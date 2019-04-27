package com.prospect.core.domain.project

data class IceBoxItems(private val items: List<String> = listOf()){

    fun add(featureId: String): IceBoxItems {
        if (contain(featureId)) throw IllegalArgumentException("既に存在するFeatureです")

        val copy = mutableListOf(*items.toMutableList().toTypedArray())
        copy.add(featureId)
        return IceBoxItems(copy)
    }

    fun size(): Int = items.size

    fun isEmpty() = items.isEmpty()

    fun contain(featureId: String): Boolean = items.contains(featureId)

}


package com.prospect.core.domain.project

data class IceBoxItems(private val items: List<String> = listOf()){

    fun add(featureId: String): IceBoxItems {
        if (contain(featureId)) throw IllegalArgumentException("既に存在するFeatureです")

        val copy = mutableListOf(*items.toTypedArray())
        copy.add(featureId)
        return IceBoxItems(copy)
    }

    fun remove(featureId: String): IceBoxItems {
        if (!contain(featureId)) throw IllegalArgumentException("存在しないFeatureです")

        val copy = mutableListOf(*items.toTypedArray())
        copy.remove(featureId)
        return IceBoxItems(copy)
    }

    fun size(): Int = items.size

    fun isEmpty(): Boolean = items.isEmpty()

    fun contain(featureId: String): Boolean = items.contains(featureId)

}


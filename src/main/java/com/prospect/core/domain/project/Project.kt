package com.prospect.core.domain.project

import com.prospect.core.domain.feature.Feature

class Project(
        val id: String,
        private var iceBoxItems: Set<Feature> = setOf()
) {

    fun addIceBoxItem(feature: Feature) {
        iceBoxItems += feature
    }

    fun removeIceBoxItem(feature: Feature) {
        iceBoxItems -= feature
    }

}
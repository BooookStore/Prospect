package com.prospect.core.applicationservice

import com.prospect.core.domain.feature.FeatureRepository
import com.prospect.core.domain.type.Point
import org.springframework.stereotype.Service

@Service
class FeatureApplicationService(private val featureRepository: FeatureRepository) {

    fun remove(featureId: String) {
        val anFeature = featureRepository.findById(featureId) ?: throw IllegalArgumentException("存在しないFeatureです")
        featureRepository.remove(anFeature)
    }

    fun change(aCommand: FeatureChangeCommand) {
        val anFeature = featureRepository.findById(aCommand.id) ?: throw IllegalArgumentException("存在しないFeatureです")

        anFeature.apply {
            title = aCommand.title
            description = aCommand.description
            point = aCommand.point
        }

        featureRepository.save(anFeature)
    }

}

data class FeatureChangeCommand(
        val id: String,
        val title: String,
        val description: String,
        val point: Point?
)

package com.prospect.core.applicationservice

import com.prospect.core.domain.common.DomainEventPublisher
import com.prospect.core.domain.common.nexIdentify
import com.prospect.core.domain.feature.Feature
import com.prospect.core.domain.feature.FeatureRepository
import com.prospect.core.domain.feature.IceBoxItemCreatedEvent
import com.prospect.core.domain.project.ProjectRepository
import com.prospect.core.domain.type.Point
import org.springframework.stereotype.Service

@Service
class FeatureApplicationService(
        private val featureRepository: FeatureRepository,
        private val projectRepository: ProjectRepository
) {

    fun addIceBoxItem(aCommand: FeatureAddCommand) {
        projectRepository.findById(aCommand.projectId) ?: throw IllegalArgumentException("存在しないProjectです")

        val anNewFeature = Feature(
                nexIdentify(),
                aCommand.title,
                aCommand.description,
                aCommand.point
        )

        featureRepository.save(anNewFeature)

        val event = IceBoxItemCreatedEvent(
                projectId = aCommand.projectId,
                featureId = anNewFeature.id
        )

        DomainEventPublisher.publish(event)
    }

    fun remove(anId: String) {
        val anFeature = featureRepository.findById(anId) ?: throw IllegalArgumentException("存在しないFeatureです")
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

data class FeatureAddCommand(
        val projectId: String,
        val title: String,
        val description: String,
        val point: Point?
)

data class FeatureChangeCommand(
        val id: String,
        val title: String,
        val description: String,
        val point: Point?
)

package com.prospect.core.applicationservice

import com.prospect.core.domain.common.DomainEventPublisher
import com.prospect.core.domain.common.nexIdentify
import com.prospect.core.domain.feature.Feature
import com.prospect.core.domain.feature.FeatureRepository
import com.prospect.core.domain.feature.IceBoxItemCreatedEvent
import com.prospect.core.domain.feature.ProductBacklogItemCreatedEvent
import com.prospect.core.domain.project.ProjectRepository
import com.prospect.core.domain.type.Point
import org.springframework.stereotype.Service

@Service
class FeatureApplicationService(
        private val featureRepository: FeatureRepository,
        private val projectRepository: ProjectRepository
) {

    fun add(aCommand: FeatureAddCommand) {
        projectRepository.findById(aCommand.projectId) ?: throw IllegalArgumentException("存在しないProjectです")
        if (!isValidStatus(aCommand.status)) throw IllegalArgumentException("不正なステータスです")

        val anNewFeature = Feature(
                nexIdentify(),
                aCommand.title,
                aCommand.description,
                aCommand.point
        )

        featureRepository.save(anNewFeature)

        when (aCommand.status) {
            "iceBoxItem" -> publishIceBoxItemCreatedEvent(aCommand.projectId, anNewFeature.id)
            "productBacklogItem" -> publishProductBacklogItemCreatedEvent(aCommand.projectId, anNewFeature.id)
        }
    }

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

data class FeatureAddCommand(
        val projectId: String,
        val title: String,
        val description: String,
        val point: Point?,
        val status: String
)

data class FeatureChangeCommand(
        val id: String,
        val title: String,
        val description: String,
        val point: Point?
)

private fun isValidStatus(status: String): Boolean {
    return when (status) {
        "iceBoxItem" -> true
        "productBacklogItem" -> true
        else -> false
    }
}

private fun publishIceBoxItemCreatedEvent(projectId: String, featureId: String) {
    val event = IceBoxItemCreatedEvent(
            projectId = projectId,
            featureId = featureId
    )

    DomainEventPublisher.publish(event)
}

private fun publishProductBacklogItemCreatedEvent(projectId: String, featureId: String) {
    val event = ProductBacklogItemCreatedEvent(
            projectId = projectId,
            featureId = featureId
    )

    DomainEventPublisher.publish(event)
}

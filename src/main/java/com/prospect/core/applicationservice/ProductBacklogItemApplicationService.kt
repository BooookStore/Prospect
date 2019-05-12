package com.prospect.core.applicationservice

import com.prospect.core.domain.common.DomainEventPublisher
import com.prospect.core.domain.common.nexIdentify
import com.prospect.core.domain.feature.Feature
import com.prospect.core.domain.feature.FeatureRepository
import com.prospect.core.domain.feature.ProductBacklogItemCreatedEvent
import com.prospect.core.domain.project.ProjectRepository
import com.prospect.core.domain.type.Point
import com.prospect.core.domain.type.Priority
import org.springframework.stereotype.Service

@Service
class ProductBacklogItemApplicationService(
        private val projectRepository: ProjectRepository,
        private val featureRepository: FeatureRepository
) {

    fun addProductBacklogItem(aCommand: ProductBacklogItemAddCommand) {
        projectRepository.findById(aCommand.projectId) ?: throw IllegalArgumentException("存在しないProjectです")

        val anNewFeature = Feature(
                nexIdentify(),
                aCommand.title,
                aCommand.description,
                aCommand.point
        )

        featureRepository.save(anNewFeature)

        val event = ProductBacklogItemCreatedEvent(
                projectId = aCommand.projectId,
                featureId = anNewFeature.id,
                priority = Priority.of(aCommand.priority)
        )

        DomainEventPublisher.publish(event)
    }

}

data class ProductBacklogItemAddCommand(
        val projectId: String,
        val title: String,
        val description: String,
        val point: Point?,
        val priority: Int
)


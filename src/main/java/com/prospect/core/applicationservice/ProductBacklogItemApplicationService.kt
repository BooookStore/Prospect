package com.prospect.core.applicationservice

import com.prospect.core.domain.common.DomainEventPublisher
import com.prospect.core.domain.common.nexIdentify
import com.prospect.core.domain.feature.Feature
import com.prospect.core.domain.feature.FeatureRepository
import com.prospect.core.domain.feature.ProductBacklogItemCreatedEvent
import com.prospect.core.domain.project.ProjectRepository
import com.prospect.core.domain.type.Point
import org.springframework.stereotype.Service

@Service
class ProductBacklogItemApplicationService(
        private val projectRepository: ProjectRepository,
        private val featureRepository: FeatureRepository
) {

    fun addProductBacklogItem(aCommand: AddProductBacklogItemCommand) {
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
                featureId = anNewFeature.id
        )

        DomainEventPublisher.publish(event)
    }

}

data class AddProductBacklogItemCommand(
        val projectId: String,
        val title: String,
        val description: String,
        val point: Point?
)


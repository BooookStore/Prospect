package com.prospect.core.applicationservice

import com.prospect.core.domain.common.DomainEventPublisher
import com.prospect.core.domain.common.nexIdentify
import com.prospect.core.domain.feature.Feature
import com.prospect.core.domain.feature.FeatureRepository
import com.prospect.core.domain.feature.IceBoxItemCreatedEvent
import com.prospect.core.domain.feature.Point
import com.prospect.core.domain.project.ProjectRepository
import org.springframework.stereotype.Service

@Service
class IceBoxItemApplicationService(
        private val featureRepository: FeatureRepository,
        private val projectRepository: ProjectRepository
) {

    fun addIceBoxItem(aCommand: AddIceBoxItemCommand) {
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

}

data class AddIceBoxItemCommand(
        val projectId: String,
        val title: String,
        val description: String,
        val point: Point?
)


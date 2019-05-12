package com.prospect.core.domaineventlistener

import com.prospect.core.domain.feature.IceBoxItemCreatedEvent
import com.prospect.core.domain.project.ProjectRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.logging.Level
import java.util.logging.Logger

@Component
class AddIceBoxItemToProject(private val projectRepository: ProjectRepository) {

    private val logger: Logger = Logger.getLogger(AddIceBoxItemToProject::class.java.canonicalName)

    @EventListener
    @Async
    fun addIceBoxItemToProject(iceBoxItemCreatedEvent: IceBoxItemCreatedEvent) {
        logger.log(Level.INFO, "add iceBoxItem to project - feature(${iceBoxItemCreatedEvent.featureId}) to Project(${iceBoxItemCreatedEvent.projectId})")

        val project = projectRepository.findById(iceBoxItemCreatedEvent.projectId)
                ?: throw IllegalArgumentException("存在しないProjectです")

        project.addIceBoxItem(iceBoxItemCreatedEvent.featureId)
        projectRepository.save(project)
    }

}
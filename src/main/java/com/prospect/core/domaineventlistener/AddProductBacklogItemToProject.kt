package com.prospect.core.domaineventlistener

import com.prospect.core.domain.feature.ProductBacklogItemCreatedEvent
import com.prospect.core.domain.project.ProjectRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.logging.Level
import java.util.logging.Logger

/**
 * ProductBacklogItemをProjectに追加する。
 *
 * 追加されるProductBacklogItemのPriorityはProjectの最低になる。
 *
 * ProductBacklogItemの作成と同時にPriorityの変更を行う事は出来ないため、
 * 別途Priorityの変更をProjectに対して要求しなくてはならない。
 */
@Component
class AddProductBacklogItemToProject(private val projectRepository: ProjectRepository) {

    private val logger: Logger = Logger.getLogger(AddProductBacklogItemToProject::class.java.canonicalName)

    @EventListener
    @Async
    fun addProductBacklogItem(listenedEvent: ProductBacklogItemCreatedEvent) {
        logger.log(Level.INFO, "add productBacklogItem to project - feature(${listenedEvent.featureId}) to Project(${listenedEvent.projectId})")

        val project = projectRepository.findById(listenedEvent.projectId)
                ?: throw IllegalArgumentException("存在しないProjectです")

        project.addProductBacklogItemToLowestPriority(listenedEvent.featureId)

        projectRepository.save(project)
    }

}
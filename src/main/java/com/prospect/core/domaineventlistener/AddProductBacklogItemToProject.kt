package com.prospect.core.domaineventlistener

import com.prospect.core.domain.feature.ProductBacklogItemCreatedEvent
import com.prospect.core.domain.project.NotAllowancePriorityException
import com.prospect.core.domain.project.ProductBacklogItem
import com.prospect.core.domain.project.Project
import com.prospect.core.domain.project.ProjectRepository
import com.prospect.core.domain.type.Priority
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.logging.Level
import java.util.logging.Logger

@Component
class AddProductBacklogItemToProject(private val projectRepository: ProjectRepository) {

    private val logger: Logger = Logger.getLogger(AddProductBacklogItemToProject::class.java.canonicalName)

    @EventListener
    @Async
    fun addProductBacklogItem(listenedEvent: ProductBacklogItemCreatedEvent) {
        logger.log(Level.INFO, "add productBacklogItem to project - feature(${listenedEvent.featureId}) to Project(${listenedEvent.projectId})")

        val project = projectRepository.findById(listenedEvent.projectId)
                ?: throw IllegalArgumentException("存在しないProjectです")

        val aNewProductBacklogItem = ProductBacklogItem(
                listenedEvent.featureId,
                listenedEvent.priority
        )

        try {
            project.addProductBacklogItem(aNewProductBacklogItem)
        } catch (e: NotAllowancePriorityException) {
            // 優先度が異常である場合、最低優先度でプロジェクトに追加する
            val priorityChangedProductBacklogItem = aNewProductBacklogItem.changePriority(lowestPriority(project))
            project.addProductBacklogItem(priorityChangedProductBacklogItem)
        }

        projectRepository.save(project)
    }

    /**
     * [Project]が保持する[ProductBacklogItem]の最低優先度を取得。
     */
    private fun lowestPriority(project: Project): Priority =
            project.findLowestProductBacklogItem()?.priority?.down() ?: Priority.of(1)

}
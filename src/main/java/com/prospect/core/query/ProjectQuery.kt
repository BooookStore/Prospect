package com.prospect.core.query

import com.prospect.core.domain.project.Project
import com.prospect.core.query.model.ProductBacklogItemModel
import com.prospect.core.query.model.ProjectDetailModel
import com.prospect.core.query.model.ProjectOverviewModel
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service

@Service
class ProjectQuery(private val mongoTemplate: MongoTemplate) {

    fun find(projectCriteria: ProjectCriteria): List<ProjectOverviewModel> {
        val skipAmount = projectCriteria.amount * projectCriteria.offset

        val aggregation = newAggregation(
                sort(ASC, projectCriteria.sort),
                skip(skipAmount),
                limit(projectCriteria.amount),
                project("id", "name")
        )

        return mongoTemplate.aggregate(
                aggregation,
                Project::class.java,
                ProjectOverviewModel::class.java)
                .mappedResults
    }

    fun findById(id: String): ProjectDetailModel =
            mongoTemplate.findOne(query(where("id").isEqualTo(id)), Project::class.java)?.toProjectDetailModel()
                    ?: throw IllegalArgumentException("存在しないプロジェクトです")

}

data class ProjectCriteria(
        val amount: Long,
        val offset: Long,
        val sort: String
)

private fun Project.toProjectDetailModel(): ProjectDetailModel {
    val iceBoxItems = iceBoxItems.items.toList()
    val productBacklogItemModels = productBacklogItems.orderedItems.elements.map { ProductBacklogItemModel(it.featureId, it.priority.value) }

    return ProjectDetailModel(
            id,
            name,
            iceBoxItems,
            productBacklogItemModels
    )
}

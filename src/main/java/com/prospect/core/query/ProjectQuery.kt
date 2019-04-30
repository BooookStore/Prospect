package com.prospect.core.query

import com.prospect.core.domain.project.Project
import com.prospect.core.query.model.ProjectOverviewModel
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.stereotype.Service

@Service
class ProjectQuery(private val mongoTemplate: MongoTemplate) {

    fun find(criteria: Criteria): List<ProjectOverviewModel> {
        val skipAmount = criteria.amount * criteria.offset

        val aggregation = newAggregation(
                sort(ASC, criteria.sort),
                skip(skipAmount),
                limit(criteria.amount),
                project("id", "name")
        )

        return mongoTemplate.aggregate(
                aggregation,
                Project::class.java,
                ProjectOverviewModel::class.java)
                .mappedResults
    }

}

data class Criteria(
        val amount: Long,
        val offset: Long,
        val sort: String
)

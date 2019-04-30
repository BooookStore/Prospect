package com.prospect.core.query.model

import com.prospect.core.domain.project.Project
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class ProjectQuery(private val mongoTemplate: MongoTemplate) {

    fun find(criteria: Criteria): List<Project> {
        val skipAmount = (criteria.amount * criteria.offset).toLong()
        val query = Query()
                .skip(skipAmount)
                .limit(criteria.amount)
                .with(Sort(Sort.Direction.ASC, criteria.sort.toString()))
        return mongoTemplate.find(query, Project::class.java)
    }

}

data class Criteria(
        val amount: Int,
        val offset: Int,
        val sort: SORT
)

enum class SORT {
    NAME
}

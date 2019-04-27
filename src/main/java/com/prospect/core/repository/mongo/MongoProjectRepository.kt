package com.prospect.core.repository.mongo

import com.prospect.core.domain.project.Project
import com.prospect.core.domain.project.ProjectRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Repository
class MongoProjectRepository(private val mongoTemplate: MongoTemplate) : ProjectRepository {

    override fun findById(id: String): Project? =
            mongoTemplate.findOne(query(where("id").isEqualTo(id)), Project::class.java)

    override fun save(project: Project) {
        mongoTemplate.save(project)
    }

    override fun remove(project: Project) {
        mongoTemplate.remove(project)
    }

}
package com.prospect.core.repository.mongo

import com.prospect.core.domain.feature.Feature
import com.prospect.core.domain.feature.FeatureRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
@Repository
class MongoFeatureRepository(private val mongoTemplate: MongoTemplate) : FeatureRepository {

    override fun findById(id: String): Feature? =
            mongoTemplate.findOne(query(where("id").isEqualTo(id)))

    override fun save(feature: Feature) {
        mongoTemplate.save(feature)
    }

    override fun remove(feature: Feature) {
        mongoTemplate.remove(feature)
    }

}
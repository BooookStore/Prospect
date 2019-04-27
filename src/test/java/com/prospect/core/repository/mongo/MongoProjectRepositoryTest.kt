package com.prospect.core.repository.mongo

import com.prospect.core.domain.project.Project
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataMongoTest
class MongoProjectRepositoryTest {

    lateinit var mongoProjectRepository: MongoProjectRepository

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Before
    fun before() {
        mongoProjectRepository = MongoProjectRepository(mongoTemplate)
    }

    @Test
    fun saveAndRemoveTest() {
        mongoProjectRepository.save(Project("1", "CafeRenewalProject"))
        val savedProject = mongoProjectRepository.findById("1")

        assertThat(savedProject)
                .isNotNull
                .hasFieldOrPropertyWithValue("id", "1")
                .hasFieldOrPropertyWithValue("name", "CafeRenewalProject")

        mongoProjectRepository.remove(savedProject!!)
        val nullProject = mongoProjectRepository.findById("1")

        assertThat(nullProject).isNull()
    }

}

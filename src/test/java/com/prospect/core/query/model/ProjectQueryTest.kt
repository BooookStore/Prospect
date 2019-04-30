package com.prospect.core.query.model

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
class ProjectQueryTest {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    lateinit var projectQuery: ProjectQuery

    @Before
    fun before() {
        projectQuery = ProjectQuery(mongoTemplate)
    }

    @Test
    fun find() {
        // setup
        mongoTemplate.save(Project("1", "A"))
        mongoTemplate.save(Project("2", "B"))
        mongoTemplate.save(Project("3", "C"))
        mongoTemplate.save(Project("4", "D"))

        // execute
        projectQuery.find(Criteria(2, 0, "name")).let { projects ->
            // verify
            assertThat(projects)
                    .hasSize(2)
                    .containsSequence(
                            ProjectOverviewModel("1", "A"),
                            ProjectOverviewModel("2", "B")
                    )
        }

        // execute
        projectQuery.find(Criteria(2, 1, "name")).let { projects ->
            // verify
            assertThat(projects)
                    .hasSize(2)
                    .containsSequence(
                            ProjectOverviewModel("3", "C"),
                            ProjectOverviewModel("4", "D")
                    )
        }
    }
}
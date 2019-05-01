package com.prospect.core.query.model

import com.prospect.core.domain.project.IceBoxItems
import com.prospect.core.domain.project.ProductBacklogItem
import com.prospect.core.domain.project.ProductBacklogItems
import com.prospect.core.domain.project.Project
import com.prospect.core.domain.type.Priority
import com.prospect.core.query.ProjectCriteria
import com.prospect.core.query.ProjectQuery
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
        projectQuery.find(ProjectCriteria(2, 0, "name")).let { projects ->
            // verify
            assertThat(projects)
                    .hasSize(2)
                    .containsSequence(
                            ProjectOverviewModel("1", "A"),
                            ProjectOverviewModel("2", "B")
                    )
        }

        // execute
        projectQuery.find(ProjectCriteria(2, 1, "name")).let { projects ->
            // verify
            assertThat(projects)
                    .hasSize(2)
                    .containsSequence(
                            ProjectOverviewModel("3", "C"),
                            ProjectOverviewModel("4", "D")
                    )
        }
    }

    @Test
    fun findById() {
        // setup
        val iceBoxItems = IceBoxItems().add("1").add("2").add("3")
        val productBacklogItems = ProductBacklogItems()
                .add(ProductBacklogItem("1", Priority.of(1)))
                .add(ProductBacklogItem("2", Priority.of(2)))
                .add(ProductBacklogItem("3", Priority.of(3)))

        mongoTemplate.save(Project("1", "A", iceBoxItems, productBacklogItems))

        // execute
        projectQuery.findById("1").let { projectDetailModel ->
            // verify
            assertThat(projectDetailModel)
                    .isNotNull
                    .hasFieldOrPropertyWithValue("id", "1")
            assertThat(projectDetailModel.iceBoxItems)
                    .contains("1", "2", "3")
            assertThat(projectDetailModel.productBacklogItems)
                    .contains(
                            ProductBacklogItemModel("1", 1),
                            ProductBacklogItemModel("2", 2),
                            ProductBacklogItemModel("3", 3)
                    )
        }
    }

}
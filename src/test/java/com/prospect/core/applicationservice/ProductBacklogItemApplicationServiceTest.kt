package com.prospect.core.applicationservice

import com.prospect.core.domain.feature.Feature
import com.prospect.core.domain.feature.Point
import com.prospect.core.domain.project.Project
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class ProductBacklogItemApplicationServiceTest {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Autowired
    lateinit var productBacklogItemApplicationService: ProductBacklogItemApplicationService

    @Test
    fun addProductBacklogItemTest() {
        // setup for mongodb
        mongoTemplate.save(Project("1", "CafeRenewalProject"))

        // execute
        productBacklogItemApplicationService.addProductBacklogItem(
                AddProductBacklogItemCommand(
                        projectId = "1",
                        title = "メール配信できる",
                        description = "特定のグループにメールを配信できる",
                        point = Point.of(1)
                )
        )

        // verify for feature added
        val feature: Feature? = mongoTemplate.findOne(query(where("title").isEqualTo("メール配信できる")))
        assertThat(feature).isNotNull

        // verify project updated
        val project: Project? = mongoTemplate.findOne(query(where("id").isEqualTo("1")))
        assertThat(project!!.productBacklogItems.items).hasSize(1)
    }

}
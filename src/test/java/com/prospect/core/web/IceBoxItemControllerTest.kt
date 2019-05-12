package com.prospect.core.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.prospect.core.domain.feature.Feature
import com.prospect.core.domain.project.Project
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringRunner::class)
@SpringBootTest
class IceBoxItemControllerTest {

    @Autowired
    lateinit var wac: WebApplicationContext

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mapper: MappingJackson2HttpMessageConverter

    lateinit var mockMvc: MockMvc

    @Before
    fun before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
    }

    @Test
    fun addIceBoxItemTest() {
        // setup
        mongoTemplate.save(Project("1", "CafeRenewalProject"))

        val jsonContent = objectMapper.writeValueAsString(AddIceBoxItemCommand(
                title = "後払いできる",
                description = "顧客は注文金額をQRコードを用いて後払いすることができる",
                point = 0
        ))

        // execute
        mockMvc.perform(post("/project/1/iceboxitem")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonContent))

        // verify for iceBoxItem saved
        val feature = mongoTemplate.findOne(query(where("title").isEqualTo("後払いできる")), Feature::class.java)
        assertThat(feature).isNotNull

        // verify for project updated
        val updatedProject = mongoTemplate.findOne(query(where("id").isEqualTo("1")), Project::class.java)!!
        assertThat(updatedProject.iceBoxItems.size()).isEqualTo(1)
    }

}


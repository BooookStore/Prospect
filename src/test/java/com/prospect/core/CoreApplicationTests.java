package com.prospect.core;

import com.prospect.core.domain.feature.Feature;
import com.prospect.core.domain.type.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CoreApplicationTests {

    @Autowired
    MongoDbFactory mongoDbFactory;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void contextLoads() {
        mongoTemplate.insert(
                new Feature("1", "Title", "Description", new Point(10))
        );
    }

}

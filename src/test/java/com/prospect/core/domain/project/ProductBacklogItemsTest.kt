package com.prospect.core.domain.project

import com.prospect.core.domain.type.Priority
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ProductBacklogItemsTest {

    @Test
    fun whenCreatedDoesntHaveTest() {
        assertThat(ProductBacklogItems().isEmpty())
    }

    @Test
    fun addTest() {
        // (1) add one for empty ProductBacklogItems
        // setup
        val emptyProductBacklogItems = ProductBacklogItems()

        // execute
        val hasOneProductBacklogItems = emptyProductBacklogItems.add(ProductBacklogItem("1", Priority.of(1)))

        // verify
        assertThat(emptyProductBacklogItems.isEmpty()).isTrue()
        assertThat(hasOneProductBacklogItems.size())
                .isEqualTo(1)
        assertThat(hasOneProductBacklogItems.findByPriority(Priority.of(1)))
                .isNotNull
                .isEqualTo(ProductBacklogItem("1", Priority.of(1)))

        // (2) add one for size 1 ProductBacklogItems
        // execute
        val hasTwoProductBacklogItems = hasOneProductBacklogItems.add(ProductBacklogItem("2", Priority.of(1)))

        // verify
        assertThat(hasTwoProductBacklogItems.size()).isEqualTo(2)
        assertThat(hasTwoProductBacklogItems.findByPriority(Priority.of(1)))
                .isEqualTo(ProductBacklogItem("2", Priority.of(1)))
        assertThat(hasTwoProductBacklogItems.findByPriority(Priority.of(2)))
                .isEqualTo(ProductBacklogItem("1", Priority.of(2)))

        // (3) add one for size 2 ProductBacklogItems to center
        // execute
        val hasThreeProductBacklogItem = hasTwoProductBacklogItems.add(ProductBacklogItem("3", Priority.of(2)))

        // verify
        assertThat(hasThreeProductBacklogItem.size()).isEqualTo(3)
        assertThat(hasThreeProductBacklogItem.findByPriority(Priority.of(1)))
                .isEqualTo(ProductBacklogItem("2", Priority.of(1)))
        assertThat(hasThreeProductBacklogItem.findByPriority(Priority.of(2)))
                .isEqualTo(ProductBacklogItem("3", Priority.of(2)))
        assertThat(hasThreeProductBacklogItem.findByPriority(Priority.of(3)))
                .isEqualTo(ProductBacklogItem("1", Priority.of(3)))
    }

}
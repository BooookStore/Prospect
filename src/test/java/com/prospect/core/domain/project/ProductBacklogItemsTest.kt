package com.prospect.core.domain.project

import com.prospect.core.domain.type.Priority
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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

    @Test
    fun illegalPriorityProductBacklogItemAddTest() {
        // setup
        val productBacklogItems = ProductBacklogItems().add(ProductBacklogItem("1", Priority.of(1)))

        // execute and verify
        assertThatThrownBy {
            productBacklogItems.add(ProductBacklogItem("2", Priority.of(3)))
        }.isExactlyInstanceOf(IllegalArgumentException::class.java)

        // verify not thrown
        productBacklogItems.add(ProductBacklogItem("2", Priority.of(2)))
    }

    @Test
    fun removeTest() {
        // (1) remove from size 3 ProductBacklogItems in middle
        // setup
        val hasThreeProductBacklogItems = ProductBacklogItems()
                .add(ProductBacklogItem("1", Priority.of(1)))
                .add(ProductBacklogItem("2", Priority.of(2)))
                .add(ProductBacklogItem("3", Priority.of(3)))

        // execute
        val hasTwoProductBacklogItems = hasThreeProductBacklogItems.remove(ProductBacklogItem("2", Priority.of(2)))

        // verify
        assertThat(hasTwoProductBacklogItems.size()).isEqualTo(2)
        assertThat(hasTwoProductBacklogItems.findByPriority(Priority.of(1)))
                .isEqualTo(ProductBacklogItem("1", Priority.of(1)))
        assertThat(hasTwoProductBacklogItems.findByPriority(Priority.of(2)))
                .isEqualTo(ProductBacklogItem("3", Priority.of(2)))

        // (2) remove from size 2 ProductBacklogItems in head
        // execute
        val hasOneProductBacklogItems = hasTwoProductBacklogItems.remove(ProductBacklogItem("1", Priority.of(1)))

        // verify
        assertThat(hasOneProductBacklogItems.size()).isEqualTo(1)
        assertThat(hasOneProductBacklogItems.findByPriority(Priority.of(1)))
                .isEqualTo(ProductBacklogItem("3", Priority.of(1)))

        // (3) remove from size 1 ProductBacklogItems
        // execute
        val emptyProductBacklogItems = hasOneProductBacklogItems.remove(ProductBacklogItem("3", Priority(1)))
        assertThat(emptyProductBacklogItems.isEmpty()).isTrue()
    }
}
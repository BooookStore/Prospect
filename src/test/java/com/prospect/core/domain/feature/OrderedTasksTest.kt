package com.prospect.core.domain.feature

import com.prospect.core.domain.type.Order
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class OrderedTasksTest {

    @Test
    fun addTest() {
        // (1) add empty orderedtasks
        // setup
        val empty = OrderedTasks()

        // execute
        val hasOne = empty.add(OrderedTask("1", Order.of(1)))

        // verify
        assertThat(empty.isEmpty()).isTrue()
        assertThat(hasOne.size()).isEqualTo(1)

        // (2) add orderedtasks one length
        // execute
        val hasTwo = hasOne.add(OrderedTask("2", Order.of(2)))

        // verify
        assertThat(hasOne.size()).isEqualTo(1)
        assertThat(hasTwo.size()).isEqualTo(2)
    }

    @Test
    fun removeTest() {
        // setup
        val hasTwo = OrderedTasks().add(OrderedTask("1", Order.of(1))).add(OrderedTask("2", Order.of(2)))

        // execute
        val hasOne = hasTwo.remove(OrderedTask("1", Order.of(1)))

        // verify
        assertThat(hasTwo.size()).isEqualTo(2)
        assertThat(hasOne.size()).isEqualTo(1)
        assertThat(hasOne.tasks).contains(OrderedTask("2", Order.of(1)))
    }

}
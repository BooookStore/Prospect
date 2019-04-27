package com.prospect.core.domain.project

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class IceBoxItemsTest {

    @Test
    fun whenCreatedEmpty() {
        assertThat(IceBoxItems().isEmpty()).isTrue()
    }

    @Test
    fun addTest() {
        // (1) add one for empty IceBoxItems
        // setup
        val emptyIceBoxItems = IceBoxItems()

        // execute
        val hasOne = emptyIceBoxItems.add("1")

        // verify
        assertThat(emptyIceBoxItems.isEmpty()).isTrue()
        assertThat(hasOne.size()).isEqualTo(1)
        assertThat(hasOne.contain("1")).isTrue()

        // (2) add second item
        // execute
        val hasTwo = hasOne.add("2")

        // verify
        assertThat(hasOne.size()).isEqualTo(1)
        assertThat(hasTwo.size()).isEqualTo(2)
        assertThat(hasTwo.contain("1")).isTrue()
        assertThat(hasTwo.contain("2")).isTrue()
    }

    @Test
    fun addDuplicateTest() {
        // setup
        val hasOne = IceBoxItems().add("1")

        // verify
        assertThatThrownBy {
            hasOne.add("1")
        }.isExactlyInstanceOf(IllegalArgumentException::class.java)
    }

}
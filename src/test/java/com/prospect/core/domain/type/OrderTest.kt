package com.prospect.core.domain.type

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class OrderTest {

    @Test
    fun comparableTest() {
        assertFalse(Order(5) > Order(4))
        assertTrue(Order(5) < Order(4))
    }

}
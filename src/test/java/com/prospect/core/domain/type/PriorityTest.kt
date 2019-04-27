package com.prospect.core.domain.type

import org.junit.Test

import org.junit.Assert.*

class PriorityTest {

    @Test
    fun compareTo() {
        assertTrue(Priority.of(1) > Priority.of(2))
        assertFalse(Priority.of(1) < Priority.of(2))
    }
}
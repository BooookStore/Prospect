package com.prospect.core.domain.type

import com.prospect.core.domain.feature.Point
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class PointTest {

    @Test
    fun compareTo() {
        assertTrue(Point(10) < Point(20))
        assertFalse(Point(30) < Point(20))
    }

}
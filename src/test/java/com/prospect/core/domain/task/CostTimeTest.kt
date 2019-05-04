package com.prospect.core.domain.task

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class CostTimeTest {

    @Test
    fun instantiate() {
        CostTime(10, 10)
        assertThatThrownBy {
            CostTime(-10, -10)
        }.isExactlyInstanceOf(IllegalArgumentException::class.java)
    }

}
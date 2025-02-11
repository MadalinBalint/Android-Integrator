package com.mendelin.androidintegrator.shared

import org.junit.Assert.*
import org.junit.Test

class StringUtilTest {
    @Test
    fun `EXPECT correct number of decimals WHEN calling round with a decimals value`() {
        val sut = 3.14159

        val actual = sut.round(3)

        assertEquals("3.142", actual)
    }

    @Test
    fun `EXPECT correct number of decimals WHEN calling round with default decimals value`() {
        val sut = 3.14159

        val actual = sut.round()

        assertEquals("3.14159000", actual)
    }
}

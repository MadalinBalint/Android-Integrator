package com.mendelin.androidintegrator.shared

import org.junit.Assert.assertEquals
import org.junit.Test


class AiResultTest {
    @Test
    fun `EXPECT success callback on reduce WHEN AiResult is Success`() {
        val sut = AiResult.Success("success")

        val actual = sut.reduce(onSuccess = { it }, onFailure = {})

        assertEquals("success", actual)
    }

    @Test
    fun `EXPECT failure callback on reduce WHEN AiResult is Failure`() {
        val sut = AiResult.Failure("failure")

        val actual = sut.reduce(onSuccess = {}, onFailure = { it })

        assertEquals("failure", actual)
    }
}

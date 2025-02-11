package com.mendelin.androidintegrator.testing

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals

class FlowTestObserver<T>(private val flow: Flow<T>, coroutineScope: CoroutineScope) {
    private val emittedValues = mutableListOf<T>()
    private var flowError: Throwable? = null
    private val job: Job = flow.onEach {
        emittedValues.add(it)
    }.catch {
        flowError = it
    }.launchIn(coroutineScope)

    fun stopObserving() {
        job.cancel()
    }

    fun getValues() = emittedValues.toList()

    fun getFlow() = flow

    fun value(): T = emittedValues.last()

    fun <T> assertValueHistory(history: List<T>) {
        assertEquals(history.size, emittedValues.size)
        assertEquals(history, emittedValues)
    }
}

fun <T> Flow<T>.test(coroutineScope: CoroutineScope = CoroutineScope(StandardTestDispatcher())) =
    FlowTestObserver(this, coroutineScope)

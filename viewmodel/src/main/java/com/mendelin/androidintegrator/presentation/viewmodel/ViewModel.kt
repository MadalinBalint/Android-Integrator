package com.mendelin.androidintegrator.presentation.viewmodel

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.*

interface StateViewModel<ScreenState> {
    val state: StateFlow<ScreenState>
}

interface EventViewModel<ScreenEvent> {
    val event: Flow<ScreenEvent>
}

class StateDelegate<ScreenState>(initialValue: ScreenState) : StateViewModel<ScreenState> {
    private var _state: MutableStateFlow<ScreenState> = MutableStateFlow(initialValue)
    override val state = _state.asStateFlow()

    fun updateState(block: () -> ScreenState) {
        _state.update {
            block()
        }
    }
}

class EventDelegate<ScreenEvent> : EventViewModel<ScreenEvent> {
    private val _event = MutableSharedFlow<ScreenEvent>()
    override val event = _event.asSharedFlow()

    suspend fun sendEvent(event: ScreenEvent) {
        _event.emit(event)
    }
}

@Composable
fun <T> EventViewModel<T>.ProcessEvents(block: (T) -> Unit) {
    LaunchedEffect(event) {
        event.onEach {
            block(it)
        }.launchIn(this)
    }
}

package com.example.smartrevisionplanner.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base MVI ViewModel with reducer pattern.
 * @param I Intent type
 * @param S State type
 * @param initialState Initial state for the screen
 */
abstract class MviViewModel<I : MviIntent, S : MviState>(
    initialState: S
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _intents = MutableSharedFlow<I>(extraBufferCapacity = 64)

    init {
        viewModelScope.launch {
            _intents.collect { intent ->
                handleIntent(intent)
            }
        }
    }

    fun sendIntent(intent: I) {
        _intents.tryEmit(intent)
    }

    protected fun updateState(update: (S) -> S) {
        _state.update(update)
    }

    protected abstract fun handleIntent(intent: I)
}

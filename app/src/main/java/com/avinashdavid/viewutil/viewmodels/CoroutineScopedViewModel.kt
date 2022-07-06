package com.avinashdavid.viewutil.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * View model that creates and destroys a coroutinescope for use in the viewmodel
 *
 */
abstract class CoroutineScopedViewModel : ViewModel() {
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
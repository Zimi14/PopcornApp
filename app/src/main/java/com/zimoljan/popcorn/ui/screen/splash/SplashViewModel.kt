package com.zimoljan.popcorn.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zimoljan.popcorn.BuildConfig
import com.zimoljan.popcorn.utils.flow.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val versionNumber: String = "v${BuildConfig.VERSION_NAME}"
        const val SPLASH_DELAY = 1500L
    }

    private val _isSplashTimeFinished = mutableEventFlow<Boolean>()
    val isSplashTimeFinished: SharedFlow<Boolean> = _isSplashTimeFinished

    init {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            _isSplashTimeFinished.emit(true)
        }
    }
}
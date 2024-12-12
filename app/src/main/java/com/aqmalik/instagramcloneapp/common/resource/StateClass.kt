package com.aqmalik.instagramcloneapp.common.resource

sealed class State {
    data object Idle : State()
    data object Loading : State()
    data object Success : State()
    data class Progress(val progress: Int) : State()
    data class Error(val message: String) : State()


}


sealed class NavigationEvent {
    data class NavigateToRoute(val route: String) : NavigationEvent()
    data class ShowError(val message: String) : NavigationEvent()
}
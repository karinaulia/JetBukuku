package com.bangkit.jetbukuku.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Library : Screen("library")
    object Profile : Screen("profile")
    object DetailBook : Screen("home/{rewardId") {
        fun createRoute(bookId: Long) = "home/$bookId"
    }
}

package com.bangkit.jetbukuku

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.jetbukuku.ui.navigation.NavigationItem
import com.bangkit.jetbukuku.ui.navigation.Screen
import com.bangkit.jetbukuku.ui.screen.home.HomeScreen

@Composable
fun JetBukukuApp(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailBook.route) {
                BottomBar(navController)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { bookId ->
                        navController.navigate(Screen.DetailBook.createRoute(bookId))
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = painterResource(R.drawable.ic_baseline_home_24),
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_library),
                icon = painterResource(R.drawable.ic_baseline_library_books_24),
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = painterResource(R.drawable.ic_baseline_person_24),
                screen = Screen.Home
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title)}
            )
        }
    }
}

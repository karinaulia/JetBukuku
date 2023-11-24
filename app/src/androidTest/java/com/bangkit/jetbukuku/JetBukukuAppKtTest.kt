package com.bangkit.jetbukuku

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.bangkit.jetbukuku.model.FakeBookDataSource
import com.bangkit.jetbukuku.ui.navigation.Screen
import com.bangkit.jetbukuku.ui.theme.JetBukukuTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetBukukuAppKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetBukukuTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetBukukuApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_library)).performClick()
        navController.assertCurrentRouteName(Screen.Library.route)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_profile)).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.menu_home)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("BookList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeBookDataSource.dummyBooks[10].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailBook.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}
package com.student24304.simplenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.student24304.simplenavigation.components.SearchBar
import com.student24304.simplenavigation.ui.theme.SimpleNavigationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            val currentDestination = navBackStackEntry?.destination
            val searchQuery = remember { mutableStateOf(TextFieldValue()) }
            val screens = listOf(Routes.FirstScreen, Routes.SearchScreen)
            val showBottomBar = currentDestination?.route in screens.map { it.route }

            SimpleNavigationTheme(dynamicColor = false) {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {

                    AnimatedVisibility(visible = currentDestination?.route == Routes.FirstScreen.route) {
                        LargeTopAppBar(
                            title = { Text(text = "Movies") },
                            scrollBehavior = scrollBehavior)
                    }

                    AnimatedContent(
                        targetState = currentDestination?.route == Routes.SearchScreen.route,
                        label = ""
                    ) {
                        if (it) {

                            SearchBar(query = searchQuery.value, onQueryChange = { textField ->
                                searchQuery.value = textField
                            }, onBackClick = {
                                navController.popBackStack()
                            }, onClearClick = {
                                searchQuery.value = TextFieldValue()
                            })
                        }/* else {
                            CenterAlignedTopAppBar(title = {}, actions = {
                                IconButton(onClick = { navController.navigate(Routes.SearchScreen.route) }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Search,
                                        contentDescription = null
                                    )
                                }
                                IconButton(onClick = {
                                    Toast.makeText(
                                        context, "Navigate to Settings", Toast.LENGTH_SHORT
                                    ).show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Settings,
                                        contentDescription = null
                                    )
                                }
                            })

                        }*/
                    }
                }, bottomBar = {
                    AnimatedVisibility(
                        visible = showBottomBar,
                        enter = slideInVertically(animationSpec = tween(400)) { it },
                        exit = slideOutVertically(animationSpec = tween(400)) { it },
                    ) {
                        NavigationBar {
                            screens.forEach { screen ->
                                val selected =
                                    currentDestination?.hierarchy?.any { it.route == screen.route } == true

                                NavigationBarItem(selected = selected, onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true

                                    }
                                }, icon = {
                                    screen.icon?.let {
                                        Icon(imageVector = it, contentDescription = null)
                                    }
                                })
                            }
                        }

                    }
                }) { paddingValues ->
                    AppNavigation(
                        searchQuery = searchQuery.value.text,
                        paddingValues = paddingValues,
                        navController = navController
                    )
                }
            }
        }
    }
}


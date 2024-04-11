package com.student24304.simplenavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(val route: String, val icon: ImageVector? = null) {
   data object FirstScreen : Routes("first_screen", Icons.Outlined.Apps)
   data object SecondScreen : Routes("second_screen")
   data object ThirdScreen : Routes("third_screen")
   data object SearchScreen : Routes("search_screen", Icons.Outlined.Search)
}
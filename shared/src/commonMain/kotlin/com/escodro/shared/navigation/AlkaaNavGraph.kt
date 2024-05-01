package com.escodro.shared.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.escodro.category.navigation.categoryScreenModule
import com.escodro.home.navigation.HomeScreen
import com.escodro.navigation.AlkaaDestinations
import com.escodro.navigation.NavigationAction
import com.escodro.preference.navigation.preferenceScreenModule
import com.escodro.task.navigation.taskScreenModule
import kotlinx.coroutines.flow.map

/**
 * Navigation graph of the application.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlkaaNavGraph(
    navigationAction: NavigationAction,
    modifier: Modifier = Modifier,
) {
    ScreenRegistry {
        taskScreenModule()
        categoryScreenModule()
        preferenceScreenModule()
    }

    println("AlkaaNavGraph, MaterialTheme.colors.primary: ${MaterialTheme.colorScheme.primary}")

    BottomSheetNavigator(modifier = modifier) {
        println("AlkaaNavGraph:BottomSheetNavigator, MaterialTheme.colors.primary: ${MaterialTheme.colorScheme.primary}")

        Navigator(screen = HomeScreen()) { navigator ->
            println("AlkaaNavGraph:BottomSheetNavigator:Navigator, MaterialTheme.colors.primary: ${MaterialTheme.colorScheme.primary}")
        }
    }
}

@Composable
private fun processNavigationAction(navigator: Navigator, action: NavigationAction) =
    when (action) {
        is NavigationAction.Home -> {
            /* Do nothing - Home is always the first screen in Alkaa */
        }

        is NavigationAction.TaskDetail -> {
            val screen = ScreenRegistry.get(AlkaaDestinations.Task.TaskDetail(action.taskId))
            navigator.push(screen)
        }
    }

@Composable
private fun closeKeyboardOnBottomSheetDismiss() {
    val keyboardController = LocalSoftwareKeyboardController.current
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
    LaunchedEffect(Unit) {
        snapshotFlow { bottomSheetNavigator.isVisible }
            .map { isVisible -> !isVisible }
            .collect { keyboardController?.hide() }
    }
}

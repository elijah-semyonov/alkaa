package com.escodro.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.escodro.designsystem.AlkaaDarkColorScheme
import com.escodro.designsystem.AlkaaLightColorScheme
import com.escodro.designsystem.AlkaaTheme
import com.escodro.navigation.NavigationAction
import com.escodro.shared.model.AppThemeOptions
import com.escodro.shared.navigation.AlkaaNavGraph
import com.escodro.shared.navigation.DummThemeLocal
import com.escodro.shared.navigation.DummyTheme
import org.koin.compose.koinInject

enum class DummyTheme {
    DARK, LIGHT, UNKNOWN
}

val DummThemeLocal = staticCompositionLocalOf {
    DummyTheme.UNKNOWN
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlkaaMultiplatformApp(
    navigationAction: NavigationAction = NavigationAction.Home,
    modifier: Modifier = Modifier,
    onThemeUpdate: (isDarkTheme: Boolean) -> Unit = {},
) {
    val theme = if (isSystemInDarkTheme()) DummyTheme.DARK else DummyTheme.LIGHT
    println(theme)

    CompositionLocalProvider(
        DummThemeLocal provides theme
    ) {
        BottomSheetNavigator(modifier = modifier) {
            println(DummThemeLocal.current)
            Navigator(screen = object : Screen {
                @Composable
                override fun Content() = Unit
            }) { navigator ->
                println(DummThemeLocal.current)
            }
        }
    }
}

@Composable
private fun rememberIsDarkTheme(viewModel: AppViewModel = koinInject()): Boolean {
    val isSystemDarkTheme = isSystemInDarkTheme()

    val theme by remember(viewModel) {
        viewModel.loadCurrentTheme()
    }.collectAsState(initial = AppThemeOptions.SYSTEM)

    val isDarkTheme = when (theme) {
        AppThemeOptions.LIGHT -> false
        AppThemeOptions.DARK -> true
        AppThemeOptions.SYSTEM -> isSystemDarkTheme
    }
    return isDarkTheme
}

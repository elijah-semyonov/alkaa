package com.escodro.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.escodro.designsystem.AlkaaDarkColorScheme
import com.escodro.designsystem.AlkaaLightColorScheme
import com.escodro.designsystem.AlkaaTheme
import com.escodro.navigation.NavigationAction
import com.escodro.shared.model.AppThemeOptions
import com.escodro.shared.navigation.AlkaaNavGraph
import org.koin.compose.koinInject

@Composable
fun AlkaaMultiplatformApp(
    navigationAction: NavigationAction = NavigationAction.Home,
    modifier: Modifier = Modifier,
    onThemeUpdate: (isDarkTheme: Boolean) -> Unit = {},
) {
    println("AlkaaLightColorScheme.primary: ${AlkaaLightColorScheme.primary}")
    println("AlkaaDarkColorScheme.primary: ${AlkaaDarkColorScheme.primary}")

    val isDarkTheme = rememberIsDarkTheme()

    onThemeUpdate(isDarkTheme)
    AlkaaTheme(isDarkTheme = isDarkTheme) {
        println("isDarkTheme: $isDarkTheme")
        println("Resolved MaterialTheme.colors.primary: ${MaterialTheme.colorScheme.primary}")
        AlkaaNavGraph(
            navigationAction = navigationAction,
            modifier = modifier,
        )
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

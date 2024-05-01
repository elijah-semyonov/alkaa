package com.escodro.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator

enum class DummyTheme {
    DARK, LIGHT, UNKNOWN
}

val DummyThemeLocal = compositionLocalOf {
    DummyTheme.UNKNOWN
}


@OptIn(ExperimentalMaterialApi::class)
@Suppress("Unused", "FunctionName")
fun MainViewController() = ComposeUIViewController {
    println("ComposeUIViewController")

    val theme = if (isSystemInDarkTheme()) DummyTheme.DARK else DummyTheme.LIGHT

    CompositionLocalProvider(
        DummyThemeLocal provides theme
    ) {
        println("CompositionLocalProvider")
        BottomSheetNavigator {
            println("BottomSheetNavigator")
            println(DummyThemeLocal.current)
            Navigator(screen = object : Screen {
                @Composable
                override fun Content() = Unit
            }) { navigator ->
                println("Navigator")
                println(DummyThemeLocal.current)
            }
        }
    }
}

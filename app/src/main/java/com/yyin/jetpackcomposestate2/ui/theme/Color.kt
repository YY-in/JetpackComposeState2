package com.yyin.jetpackcomposestate2.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

private val Yellow200 = Color(0xffffeb46)
private val Yellow500 = Color(0xFFFFEB3B)
private val Yellow400 = Color(0xFFFFEE58)
private val Blue200 = Color(0xff91a4fc)
private val Blue700 = Color(0xFF1976D2)

private val DarkColors = darkColors(
    primary = Yellow200,
    secondary = Blue200,
    // ...
)
private val LightColors = lightColors(
    primary = Yellow500,
    primaryVariant = Yellow400,
    secondary = Blue700,
)

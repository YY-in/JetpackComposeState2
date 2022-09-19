package com.yyin.jetpackcomposestate2.example

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
//定义数据类
data class Elevations(val card:Dp = 0.dp)

//将数据类包装到CompositionLocal中
val LocalElevations = compositionLocalOf { Elevations()}

//定义CompositionLocal当中的一些预设的值
object CardElevation{
    val high : Elevations
        get() = Elevations(card = 10.dp )
    val low : Elevations
        get() = Elevations(card = 5.dp)
}


@Composable
fun MyCard(
    elevation: Dp = LocalElevations.current.card,
    background:Color,
    content: @Composable () -> Unit
) {
    Card(
        elevation = elevation,
        modifier = Modifier.size(200.dp),
        backgroundColor = background,
        content = content

        )
}
@Composable
@Preview
fun MyCardPreview(){
    Column {
        //从CompositionLocal当中获取定义量，隐式传惨
        CompositionLocalProvider(LocalElevations provides CardElevation.high){
            MyCard(
                background = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
            ){}
        }

        CompositionLocalProvider(LocalElevations provides CardElevation.low) {
            MyCard(
                background = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
            ) {

            }
        }
    }
}
package com.yyin.jetpackcomposestate2.example

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.yyin.jetpackcomposestate2.R

@Composable
fun GradientText(){
    Column {
        Text("Uses MaterialTheme's provided alpha")
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text("Medium value provided for LocalContentAlpha")
            Text("This is also uses the medium values")
            CompositionLocalProvider(LocalContentAlpha.provides(ContentAlpha.disabled)){
                DescendantExample()
            }
        }
        FruitText(fruitSize = 2)
    }

}

@Composable
fun DescendantExample(){
    Text("This Text uses the disabled alpha now")
}

@Composable
@Preview
fun PreviewExample(){
    GradientText()
}

@Composable
fun FruitText(fruitSize:Int){
    //在安卓当中需要访问资源先,使用当前的上下文
    val resources = LocalContext.current.resources
    val fruitText = resources.getQuantityString(R.plurals.fruit_title,fruitSize)
    Text(text = "$fruitSize $fruitText")
}
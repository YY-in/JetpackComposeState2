package com.yyin.jetpackcomposestate2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.yyin.jetpackcomposestate2.example.MyCardPreview

class MainActivity : ComponentActivity() {
    //将属性的get、set操作交给另一个对象生成
    private val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCardPreview()
        }


    }

    @Composable
    private fun TodoActivityScreen(){
//        val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())

        TodoScreen(
            items = todoViewModel.todoItems,                                   //状态向下流动
            onAddItem = { todoViewModel.addItem(it) },       //事件向上流动
            onRemoveItem = {todoViewModel.removeItem(it)},
            onStartEdit = todoViewModel::onEditItemSelected,   //直接传递函数的引用
            ondEditItemChange = todoViewModel::onEditItemChange,
            onEditDone = todoViewModel::onEditDone,
            currentlyEditing = todoViewModel.currentEditItem
        )
    }
}


@Composable
@Preview
fun Preview() {
}

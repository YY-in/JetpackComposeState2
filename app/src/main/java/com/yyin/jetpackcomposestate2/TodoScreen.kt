package com.yyin.jetpackcomposestate2


import android.text.Layout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

//整体界面
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
    currentlyEditing: TodoItem?,
    onStartEdit: (TodoItem) -> Unit,
    ondEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit,
) {
    Column {
        //当前currentlyEditing(当前编辑条目)为空，显示输入框
        //否则进入编辑模式，最顶部会显示"Editing item"文本
        val enableTopSelection = (currentlyEditing == null)

        //输入框，外加一个灰色背景
        TodoItemInputBackground(elevate = true) {
            if (enableTopSelection) {
                TodoItemEntryInput(onItemComplete = onAddItem)
            } else {
                Surface(
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
                    shape = RectangleShape,
                ) {
                    Text(
                        text = "Editing item",
                        style = MaterialTheme.typography.h6,

                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }


            }
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items) { todo ->
                //表示当前处于输入状态
                if (currentlyEditing?.id == todo.id) {
                    TodoItemInlineEditor(
                        item = todo,
                        onEditItemChange = ondEditItemChange,
                        onEditDone = onEditDone,
                        onRemoveItem = onRemoveItem
                    )
                } else {
                    TodoRow(
                        todo = todo,
                        modifier = Modifier.fillMaxWidth(),
                        onItemClicked = { onStartEdit(todo) }
                    )
                }
            }
        }

        Button(
            onClick = { onAddItem(generateRandomTodoItem()) },
            modifier = Modifier
                .padding(16.dp)
                //最大宽度
                .fillMaxWidth()
        ) {
            Text(text = "Add random item")
        }
    }

}


//待办事件的行
@Composable
fun TodoRow(
    todo: TodoItem,
    modifier: Modifier = Modifier,
    onItemClicked: (TodoItem) -> Unit,

    ) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClicked(todo) },
        //均匀分布
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = todo.task)

        Icon(
            imageVector = todo.icon.imageVector,
            contentDescription = todo.icon.contentDescription
        )
    }
}
@file:OptIn(ExperimentalComposeUiApi::class)

package com.yyin.jetpackcomposestate2


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

//顶部输入框添加一个背景
@Composable
fun TodoItemInputBackground(
    elevate: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.()->Unit //slice api

) {
    //帧动画展示Surface底部的阴影
    val animatedElevation by animateDpAsState(targetValue = if(elevate) 1.dp else 0.dp,TweenSpec(500))
    Surface(
        color=MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
        //Surface底部的阴影设置
        shape = RectangleShape,
        elevation = animatedElevation
    ) {
        Row (
            modifier = modifier.animateContentSize(animationSpec = TweenSpec(300)),
            content = content
        )
    }

}
@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit){
    //创建一个状态对象，只要这个值发生变化，界面就会重组
    val (text, setText) = remember { mutableStateOf("") }
    val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default) }
    //IconRow是否可见取决于文本框是否有文本
    val iconsVisible = text.isNotBlank()
    //点击"Add"之后需要做的事
    val submit = {
        onItemComplete(TodoItem(text))
        setIcon(TodoIcon.Default)
        setText("")
    }
    TodoItemInput(
        text=text,
        onTextChange=setText,
        icon = icon,
        onIconChange = setIcon,
        submit = submit,
        iconsVisible = iconsVisible
    )
}

//输入框
@Composable
fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean
) {
    Column {

        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)

        ) {
            TodoInputText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = { submit() }
            )
            TodoInputButton(
                onClick = { submit() },
                text = "Add",
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = text.isNotBlank()
            )
        }

        if (iconsVisible) {
            AnimatedIconRow(
                icon = icon,
                onIconChange = onIconChange,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Composable
fun TodoInputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {}
) {
    //创建软键盘控制器
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = 1,
        modifier = modifier,
        //配置软键盘,通知键盘将采取什么操作
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            Log.i("wtf", "done")
            onImeAction()
            //点击完done之后隐藏软键盘
            keyboardController?.hide()
        }),

        )
}

@Composable
fun TodoInputButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(),
        modifier = modifier,
        enabled = enabled
    ) {
        Text(text)
    }
}

// 一排图标，根据文本框是否有内容，自动收起和弹出，带动画效果
@Composable
fun AnimatedIconRow(
    //状态提升
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier,
    visible: Boolean = true
) {
    //动画创建
    val enter = remember {
        fadeIn(animationSpec = TweenSpec(300, easing = FastOutLinearInEasing))
    }
    val exit = remember {
        fadeOut(animationSpec = TweenSpec(100, easing = FastOutSlowInEasing))
    }
    Box(modifier.defaultMinSize(minHeight = 10.dp)) {
        AnimatedVisibility(
            visible = visible,
            enter = enter,
            exit = exit
        ) {
            IconRow(icon = icon, onIconChange = onIconChange)
        }
    }
}

@Composable
fun IconRow(
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        //遍历枚举
        for (todoIcon in TodoIcon.values()) {
            Log.i("wtf", "circle ")
            SelectableIconButton(
                icon = todoIcon.imageVector,
                iconContentDescription = todoIcon.contentDescription,
                onIconSelected = {
                    Log.i("wtf", "select from $todoIcon")
                    onIconChange(todoIcon)
                },
                isSelected = (todoIcon == icon)
            )
        }
    }
}

@Composable
fun SelectableIconButton(
    icon: ImageVector,
    iconContentDescription: String,
    onIconSelected: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {

    //图标选中与未选中的状态，颜色是不一样的
    val tint = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }

    TextButton(
        onClick = {
            Log.i("wtf", "click button")
            onIconSelected()
        },
        shape = CircleShape,
        modifier = modifier
    ) {
        Column {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                tint = tint
            )
            if (isSelected) {
                Box(
                    Modifier
                        .padding(top = 3.dp)
                        .width(icon.defaultWidth)
                        .height(1.dp)
                        .background(tint)
                )
            } else {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}


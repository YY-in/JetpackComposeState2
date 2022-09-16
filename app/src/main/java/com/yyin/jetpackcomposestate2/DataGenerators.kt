package com.yyin.jetpackcomposestate2


fun generateRandomTodoItem(): TodoItem {
    val message = listOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
    ).random()

    val icon =TodoIcon.values().random()
    return TodoItem(message,icon)
}

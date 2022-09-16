package com.yyin.jetpackcomposestate2

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import java.util.*

data class TodoItem(
    val task: String,
    val icon: TodoIcon = TodoIcon.Default,
    val id: UUID = UUID.randomUUID()
)

enum class TodoIcon(
    val imageVector: ImageVector,
    val contentDescription: String
) {
    Square(Icons.Default.CropSquare, "CropSquare"),
    Done(Icons.Default.Done, "Done"),
    Event(Icons.Default.Event, "Event"),
    Private(Icons.Default.PrivacyTip, "PrivacyTip"),
    Trash(Icons.Default.RestoreFromTrash, "RestoreFromTrash");


    companion object {
        val Default = Square
    }
}

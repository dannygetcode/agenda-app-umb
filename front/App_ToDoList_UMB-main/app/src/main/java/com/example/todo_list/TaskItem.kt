package com.example.todolisttutorial

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.todo_list.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

data class TaskItem(
    var name: String,
    var desc: String,
    var dueTime: LocalTime?,
    var completedDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
)
{
    fun isCompleted() = completedDate != null
    fun imageResource(): Int = if(isCompleted()) R.drawable.checked_24 else R.drawable.unchecked_24
    fun imageColor(context: Context): Int = if(isCompleted()) DarkRed(context) else black(context)

    private fun DarkRed(context: Context) = ContextCompat.getColor(context, R.color.DarkRed)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)
}
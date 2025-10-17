package com.example.todo_list.data

import com.example.todo_list.network.NoteDto
import com.example.todolisttutorial.TaskItem
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

// DTO -> UI
fun NoteDto.toTaskItem(): TaskItem =
    TaskItem(
        name = title,
        desc = content,
        dueTime = null,                 // si más adelante usas reminderAt, lo parseas aquí
        completedDate = if (archived) LocalDate.now() else null,
        id = try { UUID.fromString(id) } catch (_: Exception) { UUID.randomUUID() }
    )

// UI -> DTO (para crear/actualizar)
fun TaskItem.toDtoForCreate(): NoteDto =
    NoteDto(
        id = null,
        title = name,
        content = desc,
        color = "blue",
        pinned = false,
        archived = completedDate != null,
        reminderAt = dueTime?.let { LocalTime.of(it.hour, it.minute).toString() }
    )

fun TaskItem.toDtoForUpdate(): NoteDto =
    NoteDto(
        id = id.toString(),
        title = name,
        content = desc,
        color = "blue",
        pinned = false,
        archived = completedDate != null,
        reminderAt = dueTime?.let { LocalTime.of(it.hour, it.minute).toString() }
    )

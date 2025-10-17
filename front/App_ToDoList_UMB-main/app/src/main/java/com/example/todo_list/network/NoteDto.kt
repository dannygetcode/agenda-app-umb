package com.example.todo_list.network

data class NoteDto(
    val id: String?,
    val title: String,
    val content: String,
    val color: String = "blue",
    val pinned: Boolean = false,
    val archived: Boolean = false,
    val reminderAt: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val deletedAt: String? = null
)

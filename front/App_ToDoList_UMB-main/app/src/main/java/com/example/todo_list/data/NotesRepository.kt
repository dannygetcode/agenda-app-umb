package com.example.todo_list.data

import com.example.todo_list.network.ApiClient
import com.example.todo_list.network.NoteDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class NotesRepository {

    private val api = ApiClient.instance

    // ------- READ -------
    suspend fun getAll(): List<NoteDto> = io {
        val res = api.getNotes()
        if (!res.isSuccessful) throw HttpException(res)
        res.body().orEmpty()
    }

    suspend fun getById(id: String): NoteDto = io {
        val res = api.getNoteById(id)
        if (!res.isSuccessful || res.body() == null) throw HttpException(res)
        res.body()!!
    }

    // ------- CREATE -------
    suspend fun create(dto: NoteDto): NoteDto = io {
        val res = api.createNote(dto)
        if (!res.isSuccessful || res.body() == null) throw HttpException(res)
        res.body()!!
    }

    // ------- UPDATE -------
    suspend fun update(id: String, dto: NoteDto): NoteDto = io {
        val res = api.updateNote(id, dto)
        if (!res.isSuccessful || res.body() == null) throw HttpException(res)
        res.body()!!
    }

    // ------- DELETE (soft) -------
    suspend fun delete(id: String) = io {
        val res = api.deleteNote(id)
        if (!res.isSuccessful) throw HttpException(res)
    }

    private suspend fun <T> io(block: suspend () -> T): T =
        withContext(Dispatchers.IO) { block() }
}

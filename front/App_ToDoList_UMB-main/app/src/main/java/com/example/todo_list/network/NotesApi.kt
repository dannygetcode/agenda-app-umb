package com.example.todo_list.network

import retrofit2.Response
import retrofit2.http.*

interface NotesApi {

    @GET("api/notes")
    suspend fun getNotes(): Response<List<NoteDto>>

    @GET("api/notes/{id}")
    suspend fun getNoteById(@Path("id") id: String): Response<NoteDto>

    @POST("api/notes")
    suspend fun createNote(@Body note: NoteDto): Response<NoteDto>

    @PUT("api/notes/{id}")
    suspend fun updateNote(@Path("id") id: String, @Body note: NoteDto): Response<NoteDto>

    @DELETE("api/notes/{id}")
    suspend fun deleteNote(@Path("id") id: String): Response<Unit>
}

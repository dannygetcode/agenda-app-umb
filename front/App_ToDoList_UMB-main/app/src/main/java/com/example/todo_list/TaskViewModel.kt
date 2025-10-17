package com.example.todolisttutorial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_list.data.NotesRepository
import com.example.todo_list.data.toTaskItem
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import androidx.lifecycle.viewModelScope

import com.example.todo_list.data.toDtoForCreate
import com.example.todo_list.data.toDtoForUpdate
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel: ViewModel()
{

    var taskItems = MutableLiveData<MutableList<TaskItem>>()
    val loading = MutableLiveData(false)
    val error = MutableLiveData<String?>(null)

    private val repo = NotesRepository()

    init {
        taskItems.value = mutableListOf()
        refresh()        // carga inicial desde el backend
    }

    // ------- READ -------
    fun refresh() {
        viewModelScope.launch {
            loading.postValue(true)
            error.postValue(null)
            try {
                val remote = repo.getAll().map { it.toTaskItem() }
                taskItems.postValue(remote.toMutableList())
            } catch (e: Exception) {
                error.postValue(e.message ?: "Error cargando notas")
            } finally {
                loading.postValue(false)
            }
        }
    }

    // ------- CREATE -------
    fun addTaskItem(newTask: TaskItem) {
        viewModelScope.launch {
            try {
                val created = repo.create(newTask.toDtoForCreate()).toTaskItem()
                val list = taskItems.value ?: mutableListOf()
                list.add(0, created) // arriba
                taskItems.postValue(list)
            } catch (e: Exception) {
                error.postValue(e.message ?: "Error creando nota")
            }
        }
    }

    // ------- UPDATE -------
    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?) {
        viewModelScope.launch {
            try {
                val list = taskItems.value ?: mutableListOf()
                val idx = list.indexOfFirst { it.id == id }
                if (idx == -1) return@launch

                val edited = list[idx].copy(name = name, desc = desc, dueTime = dueTime)
                val updated = repo.update(id.toString(), edited.toDtoForUpdate()).toTaskItem()

                list[idx] = updated
                taskItems.postValue(list)
            } catch (e: Exception) {
                error.postValue(e.message ?: "Error actualizando nota")
            }
        }
    }

    // ------- COMPLETE -> lo tratamos como archivar -------
    fun setCompleted(taskItem: TaskItem) {
        viewModelScope.launch {
            try {
                val list = taskItems.value ?: mutableListOf()
                val idx = list.indexOfFirst { it.id == taskItem.id }
                if (idx == -1) return@launch

                val toggled = list[idx].copy(
                    completedDate = if (list[idx].completedDate == null) LocalDate.now() else null
                )
                val updated = repo.update(toggled.id.toString(), toggled.toDtoForUpdate()).toTaskItem()
                list[idx] = updated
                taskItems.postValue(list)
            } catch (e: Exception) {
                error.postValue(e.message ?: "Error cambiando estado")
            }
        }
    }

    // ------- DELETE (soft) -------
    fun deleteTask(id: UUID) {
        viewModelScope.launch {
            try {
                repo.delete(id.toString())
                val list = taskItems.value ?: mutableListOf()
                taskItems.postValue(list.filter { it.id != id }.toMutableList())
            } catch (e: Exception) {
                error.postValue(e.message ?: "Error eliminando nota")
            }
        }
    }
}
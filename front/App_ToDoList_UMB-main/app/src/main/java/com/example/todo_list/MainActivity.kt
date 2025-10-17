package com.example.todo_list

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_list.databinding.ActivityMainBinding
import com.example.todolisttutorial.NewTaskSheet
import com.example.todolisttutorial.TaskItem
import com.example.todolisttutorial.TaskItemAdapter
import com.example.todolisttutorial.TaskItemClickListener
import com.example.todolisttutorial.TaskViewModel

class MainActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("usuario") ?: "Usuario"
        binding.taskCount.text = "Hola $username"

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Observa el estado de carga (opcional)
        taskViewModel.loading.observe(this) { isLoading ->
            binding.progressBar?.apply {
                visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
            }
        }

        // Observa errores del backend
        taskViewModel.error.observe(this) { msg ->
            if (msg != null) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        // Configura el RecyclerView
        setRecyclerView()

        // Botón para nueva tarea
        binding.newTaskButton.setOnClickListener {
            Log.d("MainActivity", "New Task button clicked")
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }
    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this) { list ->
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(list, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    override fun deleteTaskItem(taskItem: TaskItem) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar nota")
            .setMessage("¿Deseas eliminar esta nota?")
            .setPositiveButton("Sí") { _, _ ->
                taskViewModel.deleteTask(taskItem.id)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}

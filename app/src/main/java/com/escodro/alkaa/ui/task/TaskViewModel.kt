package com.escodro.alkaa.ui.task

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableField
import android.text.TextUtils
import com.escodro.alkaa.data.local.model.Task

/**
 * [ViewModel] responsible to provide information to [com.escodro.alkaa.databinding
 * .ActivityTaskBinding].
 *
 * Created by Igor Escodro on 1/2/18.
 */
class TaskViewModel(private val navigator: TaskNavigator) : ViewModel() {

    private val contract: TaskContract = TaskContract()

    val newTask: ObservableField<String> = ObservableField()

    /**
     * Loads all tasks.
     */
    fun loadTasks() {
        contract.loadTasks().subscribe({ navigator.updateList(it) })
    }

    /**
     * Add a new task.
     */
    fun addTask() {
        val description = newTask.get()
        if (TextUtils.isEmpty(description)) {
            navigator.onEmptyField()
            return
        }

        val task = Task(description = newTask.get())
        contract.addTask(task)
                ?.doOnComplete({ onNewTaskAdded(task) })
                ?.subscribe()
    }

    /**
     * Updates the task status.
     *
     * @param task task to be updated
     */
    fun updateTaskStatus(task: Task, isCompleted: Boolean) {
        task.completed = isCompleted
        contract.updateTask(task).subscribe()

    }

    /**
     * Deletes the given task.
     *
     * @param task task to be removed
     */
    fun deleteTask(task: Task) {
        contract.deleteTask(task)
                .doOnComplete({ onTaskRemoved(task) })
                .subscribe()

    }

    private fun onNewTaskAdded(task: Task) {
        newTask.set("")
        navigator.onNewTaskAdded(task)
    }

    private fun onTaskRemoved(task: Task) {
        navigator.onTaskRemoved(task)
    }

    /**
     * A creator to build the [TaskViewModel] passing the [TaskNavigator] as parameter.
     */
    class Factory(private val navigator: TaskNavigator) :
            ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TaskViewModel(navigator) as T
        }
    }
}

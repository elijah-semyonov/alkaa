package com.escodro.domain.usecase.task.implementation

import com.escodro.domain.model.Task
import com.escodro.domain.repository.TaskRepository
import com.escodro.domain.usecase.task.UpdateTask

/**
 * Use case to update a task from the database.
 */
internal class UpdateTaskImpl(private val taskRepository: TaskRepository) : UpdateTask {

    override suspend operator fun invoke(task: Task) =
        taskRepository.updateTask(task)
}

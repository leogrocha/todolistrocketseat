package com.estudosrocketseat.todolist.task.service;

import com.estudosrocketseat.todolist.errors.ControllerNotFoundException;
import com.estudosrocketseat.todolist.errors.DatabaseException;
import com.estudosrocketseat.todolist.task.domain.Task;
import com.estudosrocketseat.todolist.task.repository.TaskRepository;
import com.estudosrocketseat.todolist.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository repository;

    public List<Task> findAll(UUID idUser) {
        return repository.findByidUser(idUser);
    }
    public Task save(Task task) {
        return repository.save(task);
    }

    public Task update(UUID id, Task task) {
        var taskAntiga = repository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Task não encontrada."));
        task.setId(id);
        validarUsuarioAlteracaoTarefa(taskAntiga.getIdUser(), task.getIdUser());
        Utils.copyNonNullProperties(task, taskAntiga);
        return repository.save(task);
    }

    public void validarUsuarioAlteracaoTarefa(UUID idUserAtual, UUID idUserNovo) {
        if(!idUserAtual.equals(idUserNovo)) {
            throw new DatabaseException("Usuário não tem permissão para alterar essa tarefa.");
        }
    }





}

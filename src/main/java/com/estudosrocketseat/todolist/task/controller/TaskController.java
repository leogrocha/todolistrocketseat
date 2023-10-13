package com.estudosrocketseat.todolist.task.controller;

import com.estudosrocketseat.todolist.task.domain.Task;
import com.estudosrocketseat.todolist.task.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService service;

    @GetMapping
    public ResponseEntity<List<Task>> findAll(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        return ResponseEntity.ok(service.findAll((UUID) idUser));
    }

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody Task task, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        task.setIdUser((UUID) idUser);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("/{id}").toUri();
        return ResponseEntity.created(uri).body(service.save(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@RequestBody Task task, @PathVariable UUID id, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        task.setIdUser((UUID) idUser);
        return ResponseEntity.ok(service.update(id, task));
    }


}

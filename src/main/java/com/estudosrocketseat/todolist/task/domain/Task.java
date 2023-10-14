package com.estudosrocketseat.todolist.task.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_task")
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;
    @Column(length = 50)
    @Size(max = 50, message = "Tamanho máximo do título de 50 caracteres.")
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    @Enumerated(EnumType.STRING)
    private PriorityTask priority;
    @CreationTimestamp
    private LocalDateTime createAt;

    private UUID idUser;






}

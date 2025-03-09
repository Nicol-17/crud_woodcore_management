package com.crud.crudProyecto.controller;

import com.crud.crudProyecto.entity.ProjectEntity;
import com.crud.crudProyecto.entity.TaskEntity;
import com.crud.crudProyecto.service.ProjectService;
import com.crud.crudProyecto.service.TaskService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Data
    public class TaskRequest {
        private TaskEntity taskEntity;
        private Long idProyecto;
    }

    // Método GET para obtener todas las tareas
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskEntity>> getAllTasks() {
        List<TaskEntity> taskList = taskService.getAll();
        return ResponseEntity.ok(taskList);
    }

    // Método GET para obtener tareas por ID de proyecto
    @GetMapping("/tasks/proyecto/{id_proyecto}")
    public ResponseEntity<List<TaskEntity>> getTasksByProyecto(@PathVariable("id_proyecto") Long idProyecto) {
        List<TaskEntity> taskList = taskService.getTasksByProyecto(idProyecto);
        return ResponseEntity.ok(taskList);
    }

    // Método GET para obtener una tarea por ID
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable("id") Long id) {
        TaskEntity task = taskService.getById(id);
        return ResponseEntity.ok(task);
    }

    // Método POST para crear una nueva tarea
    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestBody TaskRequest taskRequest) {
        TaskEntity taskEntity = taskRequest.getTaskEntity();
        Long idProyecto = taskRequest.getIdProyecto();

        ProjectEntity project = projectService.getById(idProyecto);
        if (project == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Proyecto no encontrado");
        }
        taskEntity.setProyecto(project);
        taskService.create(taskEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tarea creada exitosamente");
    }

    // Método PUT para actualizar una tarea existente
    @PutMapping("/tasks/{id}")
    public ResponseEntity<String> updateTask(@PathVariable("id") Long id, @RequestBody TaskEntity taskEntity) {
        if (id == null || taskEntity.getId() == null || !id.equals(taskEntity.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID de la tarea no coincide o es inválido.");
        }
        taskService.update(id, taskEntity);
        return ResponseEntity.ok("Tarea actualizada exitosamente");
    }

    // Método DELETE para eliminar una tarea por ID
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
        try {
            taskService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada");
        }
    }
}
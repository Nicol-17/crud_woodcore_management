package com.crud.crudProyecto.controller;

import com.crud.crudProyecto.entity.ProjectEntity;
import com.crud.crudProyecto.entity.TaskEntity;
import com.crud.crudProyecto.service.ProjectService;
import com.crud.crudProyecto.service.TaskService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Data
    public static class TaskRequest {
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

        // 🔍 Verificar si el ID del proyecto está llegando
        System.out.println("ID Proyecto recibido: " + idProyecto);

        if (idProyecto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: idProyecto es nulo.");
        }

        ProjectEntity project = projectService.getById(idProyecto);
        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Proyecto no encontrado.");
        }

        // Asignar el proyecto a la tarea
        taskEntity.setProyecto(project);
        taskService.create(taskEntity);

        System.out.println("Tarea creada con ID: " + taskEntity.getTarea_id());

        return ResponseEntity.status(HttpStatus.CREATED).body("Tarea creada exitosamente");
    }



    // Método PUT para actualizar una tarea existente
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable("id") Long id, @RequestBody TaskRequest taskRequest) {
        System.out.println("TaskEntity recibido: " + taskRequest.getTaskEntity());
        TaskEntity taskEntity = taskRequest.getTaskEntity();
        Long idProyecto = taskRequest.getIdProyecto();

        if (taskEntity == null || taskEntity.getId() == null || !id.equals(taskEntity.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID de la tarea no coincide o es inválido."));
        }

        // Imprimir los IDs para depuración
        System.out.println("ID de la tarea (URL): " + id);
        System.out.println("ID de la tarea (Cuerpo de la solicitud): " + taskEntity.getId());

        ProjectEntity project = projectService.getById(idProyecto);
        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Error: Proyecto no encontrado."));
        }

        // Buscar la tarea existente
        TaskEntity existingTask = taskService.getById(id);
        if (existingTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Tarea no encontrada."));
        }

        // Actualizar los campos de la tarea existente con los valores de taskEntity
        existingTask.setNombre_tarea(taskEntity.getNombre_tarea());
        existingTask.setPrioridad(taskEntity.getPrioridad());
        existingTask.setResumen(taskEntity.getResumen());
        existingTask.setEncargado_tarea(taskEntity.getEncargado_tarea());
        existingTask.setSprint_tarea(taskEntity.getSprint_tarea());

        // Asignar el proyecto a la tarea
        existingTask.setProyecto(project);

        taskService.update(id, existingTask);
        return ResponseEntity.ok(Map.of("message", "Tarea actualizada exitosamente"));
    }


    // Método DELETE para eliminar una tarea por ID
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id, @RequestParam(name = "idProyecto", required = false) Long idProyecto) {
        try {
            TaskEntity task = taskService.getById(id);
            if (task == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada.");
            }

            //Verificacion del proyecto al cual pertenece la tarea.
            if(idProyecto != null && !task.getProyecto().getId().equals(idProyecto)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permisos para eliminar esta tarea.");
            }

            taskService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarea no encontrada.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la tarea.");
        }
    }
}
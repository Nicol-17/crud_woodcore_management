package com.crud.crudProyecto.controller;

import com.crud.crudProyecto.entity.ProjectEntity;
import com.crud.crudProyecto.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Método GET para obtener todos los  proyectos
    @GetMapping
    public ResponseEntity<List<ProjectEntity>> getAllProject() {
        List<ProjectEntity> projectList = projectService.getAll();
        return ResponseEntity.ok(projectList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable("id") Long id) {
        ProjectEntity project = projectService.getById(id);
        return ResponseEntity.ok(project);
    }

    // Método POST para crear un nuevo proyecto
    @PostMapping
    public ResponseEntity<String> createProject(@RequestBody ProjectEntity projectEntity) {
        projectService.create(projectEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body("Proyecto creado exitosamente");
    }

    // Método PUT para actualizar un proyecto existente
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProject(@PathVariable("id") Long id, @RequestBody ProjectEntity projectEntity) {
        if (projectEntity.getId_proyecto() == null || !id.equals(projectEntity.getId_proyecto())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El ID del proyecto no coincide o es inválido.");
        }
        projectService.update(id, projectEntity);
        return ResponseEntity.ok("Proyecto actualizado exitosamente");
    }

    // Método DELETE para eliminar un proyecto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id) {
        try {
            projectService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
        }
    }
}
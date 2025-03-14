package com.crud.crudProyecto.service;

import com.crud.crudProyecto.entity.ProjectEntity;
import com.crud.crudProyecto.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public List<ProjectEntity> getAll() {
        try {
            return projectRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener proyectos", e);
        }
    }

    public void update(Long id_proyecto, ProjectEntity projectEntity) {


        Optional<ProjectEntity> entityProjectOpt = projectRepository.findById(id_proyecto);

        if (entityProjectOpt.isEmpty()) {
            throw new RuntimeException("Error al actualizar proyecto: Proyecto no encontrado");
        }

        ProjectEntity entityProject = entityProjectOpt.get();

        entityProject.setNombre_proyecto(projectEntity.getNombre_proyecto());
        entityProject.setPrioridad(projectEntity.getPrioridad());
        entityProject.setSprint_inicio(projectEntity.getSprint_inicio());
        entityProject.setSprint_final(projectEntity.getSprint_final());
        entityProject.setEncargado_proyecto(projectEntity.getEncargado_proyecto());

        projectRepository.save(entityProject);
    }

    public void create(ProjectEntity projectEntity) {
        try {
            projectRepository.save(projectEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear proyecto", e);
        }
    }

    public void delete(long id_proyecto) {
        try {
            projectRepository.deleteById(id_proyecto);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el proyecto", e);
        }
    }

    public ProjectEntity getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }
}

package com.crud.crudProyecto.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tareas_f")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarea_id", nullable = false)
    @JsonProperty("tarea_id")
    private Long tarea_id;

    @JsonProperty("nombre_task")
    private String nombre_tarea;

    @JsonProperty("prioridad")
    private String prioridad;

    @JsonProperty("des_tasks")
    private String resumen;

    @JsonProperty("encargado")
    private String encargado_tarea;

    @JsonProperty("sprint_task")
    private LocalDate sprint_tarea;

    // Relación con ProjectEntity
    @ManyToOne
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", nullable = false)
    private ProjectEntity proyecto;


    // Si deseas mantener la convención del método 'getId()'
    public Long getId() {
        return tarea_id;
    }

    public void setId(Long id) {
        this.tarea_id = id;
    }
}
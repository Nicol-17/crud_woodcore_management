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

@Table(name = "proyecto_f")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_proyecto", nullable = false)
    private Long id;

    @JsonProperty("id_proyecto")
    public Long getId_proyecto() {
        return id;
    }

    public void setId_proyecto(Long id) {
        this.id = id;
    }


    @JsonProperty("nombre_proyecto")
    private String nombre_proyecto;

    @JsonProperty("prioridad")
    private String prioridad;

    @JsonProperty("sprint_inicio")
    private LocalDate sprint_inicio;

    @JsonProperty("sprint_final")
    private LocalDate sprint_final;

    @JsonProperty("encargado_proyecto")
    private String encargado_proyecto;

}

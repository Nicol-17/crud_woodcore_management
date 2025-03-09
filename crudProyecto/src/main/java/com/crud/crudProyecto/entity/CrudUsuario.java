package com.crud.crudProyecto.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Table(name = "usuario")
public class CrudUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre_usuario;
    private Long telefono_usuario;
    private String correo;
    private String contraseña;

}


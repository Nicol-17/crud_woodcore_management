package com.crud.crudProyecto.repository;


import com.crud.crudProyecto.entity.CrudUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrudusuarioRepository extends JpaRepository<CrudUsuario, Long> {
    CrudUsuario findByCorreo(String correo);
    Optional<CrudUsuario> findById(Long id); // Método agregado para buscar por ID
}

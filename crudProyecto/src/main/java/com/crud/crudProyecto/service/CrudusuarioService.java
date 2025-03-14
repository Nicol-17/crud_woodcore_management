package com.crud.crudProyecto.service;

import com.crud.crudProyecto.entity.CrudUsuario;
import com.crud.crudProyecto.repository.CrudusuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CrudusuarioService {

    @Autowired
    CrudusuarioRepository crudusuarioRepository;

    public CrudUsuario crearyactualizarUsuario(CrudUsuario crudUsuario) {
        if (crudUsuario.getId() == null || !crudusuarioRepository.existsById(crudUsuario.getId())) {
            System.out.println("Creando nuevo usuario...");
        } else {
            System.out.println("Actualizando usuario con ID: " + crudUsuario.getId());
        }

        return crudusuarioRepository.save(crudUsuario);
    }



    public List<CrudUsuario> vercrudusuario() {
        return crudusuarioRepository.findAll();
    }

    public void eliminarusuario(Long id) {
        crudusuarioRepository.deleteById(id); // Cambiado deleteAllById() por deleteById()
    }

    public Optional<CrudUsuario> findById(Long id) {
        return crudusuarioRepository.findById(id);
    }
}
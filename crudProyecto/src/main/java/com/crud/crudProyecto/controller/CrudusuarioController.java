package com.crud.crudProyecto.controller;

import com.crud.crudProyecto.entity.CrudUsuario;
import com.crud.crudProyecto.service.CrudusuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")

@RestController

public class CrudusuarioController {

    @Autowired
    CrudusuarioService crudusuarioService;

    @GetMapping("/crudusuario")
    public ResponseEntity<List<CrudUsuario>> vercrudUsuario(){
        List<CrudUsuario> usuarios = crudusuarioService.vercrudusuario();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/crudusuario")
    public ResponseEntity<String> crearusuario(@RequestBody CrudUsuario crudUsuario) {
        if (crudUsuario.getNombre_usuario() == null ||crudUsuario.getTelefono_usuario() == null || crudUsuario.getCorreo() == null || crudUsuario.getContraseña() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faltan campos obligatorios");
        }

        crudusuarioService.crearyactualizarUsuario(crudUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
    }


    @PutMapping("/crudusuario/{id}")
    public ResponseEntity<String> modificarusuario(@RequestBody CrudUsuario crudUsuario) {
        if (crudUsuario.getId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID de usuario requerido para actualizar");
        }
        crudusuarioService.crearyactualizarUsuario(crudUsuario);
        return ResponseEntity.ok("Usuario modificado exitosamente");
    }

    @DeleteMapping("/crudusuario/{id}")
    public ResponseEntity<String> eliminarusuario(@PathVariable("id") Long id) {
        try {
            crudusuarioService.eliminarusuario(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }



}


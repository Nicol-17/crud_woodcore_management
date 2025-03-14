package com.crud.crudProyecto.controller;

import com.crud.crudProyecto.entity.CrudUsuario;
import com.crud.crudProyecto.service.CrudusuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")

@RestController

public class CrudusuarioController {

    @Autowired
    CrudusuarioService crudusuarioService;

    @GetMapping("/crudusuario/{id}")
    public ResponseEntity<CrudUsuario> obtenerUsuario(@PathVariable Long id) {
        CrudUsuario usuario = crudusuarioService.findById(id).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
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
    public ResponseEntity<String> modificarUsuario(@PathVariable("id") Long id, @RequestBody CrudUsuario crudUsuario) {
        System.out.println("ID recibido en la URL: " + id);
        System.out.println("Datos recibidos: " + crudUsuario);

        Optional<CrudUsuario> usuarioExistente = crudusuarioService.findById(id);

        if (!usuarioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        CrudUsuario usuario = usuarioExistente.get();
        usuario.setNombre_usuario(crudUsuario.getNombre_usuario());
        usuario.setTelefono_usuario(crudUsuario.getTelefono_usuario());
        usuario.setCorreo(crudUsuario.getCorreo());
        usuario.setContraseña(crudUsuario.getContraseña());

        crudusuarioService.crearyactualizarUsuario(usuario);
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
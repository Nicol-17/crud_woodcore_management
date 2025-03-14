package com.crud.crudProyecto.controller;

import com.crud.crudProyecto.entity.CrudUsuario;
import com.crud.crudProyecto.entity.Login;
import com.crud.crudProyecto.repository.CrudusuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private CrudusuarioRepository crudusuarioRepository;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Login login) {
        CrudUsuario usuario = crudusuarioRepository.findByCorreo(login.getCorreo());

        if (usuario != null && usuario.getContraseña().equals(login.getContraseña())) {
            return ResponseEntity.ok().body(java.util.Map.of(
                    "success", true,
                    "id", usuario.getId(),
                    "nombre", usuario.getNombre_usuario(),
                    "correo", usuario.getCorreo(),
                    "message", "Login exitoso. Bienvenido " + usuario.getNombre_usuario()
            ));
        } else {
            return ResponseEntity.status(401).body(java.util.Map.of(
                    "success", false,
                    "message", "Credenciales inválidas."
            ));
        }
    }


}






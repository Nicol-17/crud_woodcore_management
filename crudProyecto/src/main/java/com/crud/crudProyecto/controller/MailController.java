package com.crud.crudProyecto.controller;

import com.crud.crudProyecto.domain.EmailDTO;
import com.crud.crudProyecto.domain.EmailFileDTO;
import com.crud.crudProyecto.entity.CrudUsuario;
import com.crud.crudProyecto.repository.CrudusuarioRepository;
import com.crud.crudProyecto.service.IEmailService;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/email")
public class MailController {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private CrudusuarioRepository crudusuarioRepository;

    @GetMapping("/verifyEmail/{email}")
    public ResponseEntity<?> verifyEmail(@PathVariable String email) {
        // Busca el usuario por correo electrónico.
        CrudUsuario usuario = crudusuarioRepository.findByCorreo(email);

        if (usuario != null) {
            // El correo existe.
            return ResponseEntity.ok().build();
        } else {
            // El correo no existe.
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<?> receiveRequestEmail( @RequestBody EmailDTO emailDTO){
        System.out.println("Mensaje Recibido" + emailDTO);

        emailService.sendEmail(emailDTO.getToUser(), emailDTO.getSubject(), emailDTO.getMessage());

        Map<String, String> response = new HashMap<>();
        response.put("estado", "Enviado");

        return  ResponseEntity.ok(response);
    }

    //Enviar correo con archivo
    @PostMapping("/sendMessageFile")
    public ResponseEntity<?> receiveRequestEmailWithFile(@ModelAttribute EmailFileDTO emailFileDTO){

        try {
            String fileName = emailFileDTO.getFile().getOriginalFilename();
            Path path = Paths.get("src/mail/resources/files/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            File file = path.toFile();

            emailService.sendEmailWhiteFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), emailFileDTO.getMessage(), file);
            Map<String, String> response = new HashMap<>();
            response.put("estado", "Enviado");
            response.put("archivo", "fileName");

            return ResponseEntity.ok(response);

        }catch (Exception e){
            throw new RuntimeException("Error al enviar el Email con el archivo." + e.getMessage());

        }
    }
}

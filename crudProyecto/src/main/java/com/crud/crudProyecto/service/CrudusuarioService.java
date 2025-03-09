package com.crud.crudProyecto.service;

import com.crud.crudProyecto.entity.CrudUsuario;
import com.crud.crudProyecto.repository.CrudusuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CrudusuarioService {

    @Autowired
    CrudusuarioRepository crudusuarioRepository;

    public void crearyactualizarUsuario(CrudUsuario crudUsuario) {
        crudusuarioRepository.save(crudUsuario);
    }

    public List<CrudUsuario> vercrudusuario(){
        List<CrudUsuario> crudusuario = new ArrayList<CrudUsuario>();
        crudusuario = crudusuarioRepository.findAll();
        return crudusuario;
    }

    public void eliminarusuario(Long id) {
        crudusuarioRepository.deleteAllById(Collections.singleton(id));
    }






}

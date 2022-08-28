package com.example.animais.controller;

import com.example.animais.controller.api.UsuarioApi;
import com.example.animais.dto.UserReturnDTO;
import com.example.animais.dto.UserDTO;
import com.example.animais.model.User;
import com.example.animais.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UserController implements UsuarioApi {

    @Autowired
    private UserService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<User> postUsuario(@RequestBody UserDTO usuariosDTO){
        return usuarioService.saveUsuario(usuariosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReturnDTO> getUsuario(@PathVariable String id){return usuarioService.getUsuario(id);}

    @GetMapping
    public Page<UserReturnDTO> getUsuarios(Pageable pageable){
        return usuarioService.findAll(pageable);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> deleteUsuario(@PathVariable String id){
        return usuarioService.deleteUsuario(id);
    }
}

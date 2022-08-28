package com.example.animais.service;

import com.example.animais.dto.UserReturnDTO;
import com.example.animais.dto.UserDTO;
import com.example.animais.model.User;
import com.example.animais.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<User> saveUsuario(UserDTO usuariosDTO) {
        User usuario = modelMapper.map(usuariosDTO, User.class);
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    public ResponseEntity<UserReturnDTO> getUsuario(String id) {
        Optional<User> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()){
            UserReturnDTO usuarioRetornoDTO = modelMapper.map(usuario, UserReturnDTO.class);
            return ResponseEntity.ok(usuarioRetornoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    public Page<UserReturnDTO> findAll(Pageable pageable) {
        return new UserReturnDTO().conversorPage(usuarioRepository.findAll(pageable));
    }

    public ResponseEntity<?> deleteUsuario(String id) {
        Optional<User> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.example.animais.dto;

import com.example.animais.model.Profile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuariosDTOTest {
    private final UserDTO usuariosDTO = new UserDTO();

    @Test
    void when_SetName_GetNameShouldReturnName(){
        String name = "nome";
        usuariosDTO.setNome(name);
        assertEquals(usuariosDTO.getNome(), name);
    }

    @Test
    void when_SetPasswordGetPasswordShouldReturnPassword(){
        String password = "123456";
        usuariosDTO.setPassword(password);
        assertEquals(usuariosDTO.getPassword(), password);
    }

    @Test
    void when_SetPerfisGetPerfisShouldReturnPerfis(){
        Profile perfil = new Profile(1L,"Admin");
        usuariosDTO.setPerfis(List.of(perfil));
        assertEquals(usuariosDTO.getPerfis().get(0).getId(), perfil.getId());
    }

    @Test
    void when_Contruct_GetsShouldReturnSets(){
        String nome = "name";
        String password = "123456";
        Profile perfil = new Profile(1L,"Admin");
        UserDTO usuariosDTO1 = new UserDTO(nome,password,List.of(perfil));
        assertEquals(usuariosDTO1.getNome(), nome);
        assertEquals(usuariosDTO1.getPassword(), password);
        assertEquals(usuariosDTO1.getPerfis().get(0), perfil);
    }

}
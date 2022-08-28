package com.example.animais.dto;

import com.example.animais.model.Profile;
import com.example.animais.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRetornoDTOTest {
    private final UserReturnDTO usuarioRetornoDTO = new UserReturnDTO();

    @Test
    void when_setId_GetIdShouldReturnId(){
        String id = "23304e";
        usuarioRetornoDTO.setId(id);
        assertEquals(usuarioRetornoDTO.getId(), id);
    }

    @Test
    void when_setName_GetNameShouldReturnName(){
        String name = "Teste";
        usuarioRetornoDTO.setNome(name);
        assertEquals(usuarioRetornoDTO.getNome(), name);
    }

    @Test
    void when_Construct_GetsShouldReturnIdAndName(){
        String id = "1234";
        String nome = "Nome";
        UserReturnDTO usuarioRetornoDTO1 = new UserReturnDTO(id,nome);
        assertEquals(usuarioRetornoDTO1.getId(), id);
        assertEquals(usuarioRetornoDTO1.getNome(), nome);
    }

    @Test
    void when_conversorPage_ShouldReturnPageUsuarioRetornoDtoWhenSuccessfull(){
        User usuario = new User("111","name","password", List.of(new Profile()));
        PageImpl<User> usuariosPage = new PageImpl<>(List.of(usuario));
        Page<UserReturnDTO> usuarioRetornoDTOPage = usuarioRetornoDTO.conversorPage(usuariosPage);
        assertNotNull(usuarioRetornoDTOPage);
        assertEquals(usuario.getNome(), usuarioRetornoDTOPage.toList().get(0).getNome());
    }
}
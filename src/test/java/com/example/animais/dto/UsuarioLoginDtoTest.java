package com.example.animais.dto;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioLoginDtoTest {
    private final UserLoginDto usuarioLoginDto = new UserLoginDto();

    @Test
    void when_SetName_GetNameShouldReturnName(){
        String name = "nome";
        usuarioLoginDto.setNome(name);
        assertEquals(usuarioLoginDto.getNome(), name);
    }

    @Test
    void when_SetPasswordGetPasswordShouldReturnPassword(){
        String password = "123456";
        usuarioLoginDto.setPassword(password);
        assertEquals(usuarioLoginDto.getPassword(), password);
    }

    @Test
    void when_CallConverterShouldReturnAuthenticationToken(){
        UserLoginDto usuarioLoginDto1 = new UserLoginDto("name", "123456");
        UsernamePasswordAuthenticationToken token = usuarioLoginDto1.converter();
        assertEquals(token.getName(), usuarioLoginDto1.getNome());
    }

    @Test
    void when_CallConverterNullShouldReturnNull(){
        UserLoginDto usuarioLoginDto1 = new UserLoginDto();
        UsernamePasswordAuthenticationToken token = usuarioLoginDto1.converter();
        assertFalse(token.isAuthenticated());
    }

    @Test
    void when_Contruct_GetsShouldReturnSets(){
        String nome = "name";
        String password = "123456";
        UserLoginDto usuariosLoginDTO1 = new UserLoginDto(nome,password);
        assertEquals(usuariosLoginDTO1.getNome(), nome);
        assertEquals(usuariosLoginDTO1.getPassword(), password);
    }
}
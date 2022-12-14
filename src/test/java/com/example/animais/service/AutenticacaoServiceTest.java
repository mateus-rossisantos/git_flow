package com.example.animais.service;

import com.example.animais.model.Profile;
import com.example.animais.model.User;
import com.example.animais.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AutenticacaoServiceTest {

    private User usuario;

    @Mock
    private UserRepository usuarioRepository;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @BeforeEach
    void setup(){
        usuario = new User("123", "User", "123456", List.of(new Profile()));
    }


    @Test
    void whemLoadUserByUserNameShouldReturnUserDetails(){
        Mockito.when(usuarioRepository.findByNome(ArgumentMatchers.anyString())).thenReturn(Optional.of(usuario));
        UserDetails usuarioReturn = autenticacaoService.loadUserByUsername(usuario.getUsername());
        assertEquals(usuario.getNome(), usuarioReturn.getUsername());
    }

    @Test
    void whemLoadUserByUserNameShouldThrowUserNameNotFoundException(){
        Mockito.when(usuarioRepository.findByNome(ArgumentMatchers.anyString()))
                .thenThrow(new UsernameNotFoundException("Usuário não encontrado"));
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> autenticacaoService.loadUserByUsername(usuario.getNome()));
    }
}
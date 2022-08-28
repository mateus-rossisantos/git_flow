package com.example.animais.controller;

import com.example.animais.dto.UserReturnDTO;
import com.example.animais.dto.UserDTO;
import com.example.animais.model.Profile;
import com.example.animais.model.User;
import com.example.animais.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    private ModelMapper modelMapper;
    private User usuario;

    @Mock
    private UserService usuarioService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup(){
        modelMapper = new ModelMapper();
        usuario = new User("123","User", "123445", List.of(new Profile()));
        UserReturnDTO usuarioRetorno = modelMapper.map(usuario, UserReturnDTO.class);
        PageImpl<UserReturnDTO> usuarioPage = new PageImpl<>(List.of(usuarioRetorno));

        Mockito.when(usuarioService.findAll(ArgumentMatchers.any())).thenReturn(usuarioPage);

        Mockito.when(usuarioService.saveUsuario(ArgumentMatchers.any(UserDTO.class)))
                .thenReturn(new ResponseEntity<>(usuario, HttpStatus.CREATED));

        Mockito.when(usuarioService.getUsuario(ArgumentMatchers.anyString())).
                thenReturn(ResponseEntity.ok(usuarioRetorno));

        Mockito.when(usuarioService.deleteUsuario(ArgumentMatchers.anyString()))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @Test
    void save_Returns201AndAnUser_WhenSuccessful(){
        UserDTO usuariosDTO = modelMapper.map(this.usuario, UserDTO.class);

        User usuarioSalvo = userController.postUsuario(usuariosDTO).getBody();
        HttpStatus response = userController.postUsuario(usuariosDTO).getStatusCode();

        assertEquals(HttpStatus.CREATED, response);
        assertNotNull(usuarioSalvo);
        assertNotNull(usuarioSalvo.getId());
        assertEquals(usuario.getNome(), usuarioSalvo.getNome());
    }

    @Test
    void getUsuario_Returns200andUsuario_WhenSuccessful(){
        User usuarioTobeSaved = usuario;
        UserReturnDTO usuario = userController.getUsuario(usuarioTobeSaved.getId()).getBody();
        HttpStatus response = userController.getUsuario(usuarioTobeSaved.getId()).getStatusCode();

        assertEquals(HttpStatus.OK, response);
        assertNotNull(usuario);
        assertEquals(usuarioTobeSaved.getNome(), usuario.getNome());
    }

    @Test
    void getUsuario_ReturnsEmpty_WhenNotSuccessful(){

        Mockito.when(usuarioService.getUsuario(ArgumentMatchers.any()))
                .thenReturn(ResponseEntity.notFound().build());
        String id = "34578";
        UserReturnDTO usuarioRetornoDTO = userController.getUsuario(id).getBody();
        HttpStatus response = userController.getUsuario(id).getStatusCode();

        assertEquals(HttpStatus.NOT_FOUND, response);
        assertNull(usuarioRetornoDTO);
    }

    @Test
    void list_ReturnsListOfUsersInsidePageObject_WhenSuccessful(){
        String expectedName = usuario.getNome();
        Page<UserReturnDTO> userPage = userController.getUsuarios(null);

        assertNotNull(userPage);
        assertFalse(userPage.toList().isEmpty());
        assertEquals(expectedName, userPage.toList().get(0).getNome());
    }

    @Test
    void delete_User_WhenSuccessful(){
        User usuarioDeletado = (User) userController.deleteUsuario(usuario.getId()).getBody();
        HttpStatus response = userController.deleteUsuario(usuario.getId()).getStatusCode();

        assertEquals(HttpStatus.NO_CONTENT, response);
        assertNull(usuarioDeletado);
    }

    @Test
    void delete_return_404_WhenNotSuccessful(){
        Mockito.when(usuarioService.deleteUsuario(ArgumentMatchers.anyString()))
                .thenReturn(ResponseEntity.notFound().build());
        HttpStatus response = userController.deleteUsuario("1").getStatusCode();

        assertEquals(HttpStatus.NOT_FOUND, response);
    }
}
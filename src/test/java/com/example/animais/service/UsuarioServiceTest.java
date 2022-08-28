package com.example.animais.service;

import com.example.animais.dto.UserReturnDTO;
import com.example.animais.dto.UserDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UsuarioServiceTest {
    private ModelMapper modelMapper;
    private User usuario;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private UserRepository usuarioRepository;

    @InjectMocks
    private UserService usuarioService;

    @BeforeEach
    void setup() {
        modelMapper = new ModelMapper();
        usuario = new User("123", "User", "123456", List.of(new Profile()));
        PageImpl<User> userPage = new PageImpl<>(List.of(usuario));

        Mockito.when(usuarioRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(userPage);

        Mockito.when(usuarioRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(usuario));

        Mockito.when(usuarioRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(usuario);

        Mockito.doNothing().when(usuarioRepository).deleteById(ArgumentMatchers.anyString());

    }

    @Test
    void list_ReturnsListOfUsersInsidePageObject_WhenSuccessful(){
        String expectedName = usuario.getNome();
        Page<UserReturnDTO> userPage2 = usuarioService.findAll(null);

        assertNotNull(userPage2);
        assertFalse(userPage2.toList().isEmpty());
        assertEquals(expectedName, userPage2.toList().get(0).getNome());
    }

    @Test
    void findById_Returns200andUser_WhenSuccessful(){
        User usuarioTobeSaved = usuario;
        Mockito.when(modelMapperMock.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(usuario, UserReturnDTO.class));
        UserReturnDTO usuarioRetornoDTO = usuarioService.getUsuario(usuarioTobeSaved.getId()).getBody();
        HttpStatus response = usuarioService.getUsuario(usuarioTobeSaved.getId()).getStatusCode();

        assertEquals(HttpStatus.OK, response);
        assertNotNull(usuarioRetornoDTO);
        assertEquals(usuarioRetornoDTO.getId(), usuario.getId());
    }

    @Test
    void findById_ReturnsEmpty_WhenNotSuccessful(){
        Mockito.when(usuarioRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        String id = "2";
        UserReturnDTO usuarioRetornoDTO = usuarioService.getUsuario(id).getBody();
        HttpStatus response = usuarioService.getUsuario(id).getStatusCode();

        assertEquals(HttpStatus.NOT_FOUND, response);
        assertNull(usuarioRetornoDTO);
    }

    @Test
    void save_Returns201anUser_WhenSuccessful(){
        UserDTO usuarioTobeSaved = modelMapper.map(usuario, UserDTO.class);
        Mockito.when(modelMapperMock.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(usuario);
        User usuarioSaved = usuarioService.saveUsuario(usuarioTobeSaved).getBody();
        HttpStatus response = usuarioService.saveUsuario(usuarioTobeSaved).getStatusCode();

        assertEquals(HttpStatus.CREATED, response);
        assertEquals(usuario.getNome(), usuarioSaved.getNome());
    }

    @Test
    void delete_Animal_WhenSuccessful(){
        Mockito.when(usuarioRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(usuario));
        HttpStatus response = usuarioService.deleteUsuario("1").getStatusCode();
        assertEquals(HttpStatus.NO_CONTENT, response);
    }

    @Test
    void delete_return_404_WhenNotSuccessful(){
        Mockito.when(usuarioRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        HttpStatus response = usuarioService.deleteUsuario("1").getStatusCode();
        assertEquals(HttpStatus.NOT_FOUND, response);
    }
}
package com.example.animais.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimaisDTOTest {

    private final AnimalDTO animaisDTO = new AnimalDTO();

    @Test
    void when_SetName_GetNameShouldReturnName(){
        String name = "nome";
        animaisDTO.setNome(name);
        assertEquals(animaisDTO.getNome(), name);
    }

    @Test
    void when_SetRaca_GetRacaShouldReturnRaca(){
        String raca = "raca";
        animaisDTO.setRaca(raca);
        assertEquals(animaisDTO.getRaca(), raca);
    }

    @Test
    void when_Contruct_GetNameAndRacaShouldReturnNameAndRaca(){
        String nome = "name";
        String raca = "raca";
        AnimalDTO animaisDTO1 = new AnimalDTO(nome, raca);
        assertEquals(animaisDTO1.getNome(), nome);
        assertEquals(animaisDTO1.getRaca(), raca);
    }


}
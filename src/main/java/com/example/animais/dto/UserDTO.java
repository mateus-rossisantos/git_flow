package com.example.animais.dto;

import com.example.animais.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String nome;
    private String password;
    private List<Profile> profiles;
}

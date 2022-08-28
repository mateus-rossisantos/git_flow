package com.example.animais.dto;

import com.example.animais.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReturnDTO {
    private String id;
    private String nome;

    public Page<UserReturnDTO> conversorPage(Page<User> pageUsers) {
        return pageUsers.map(users -> {
            UserReturnDTO dto = new UserReturnDTO();
            dto.setId(users.getId());
            dto.setNome(users.getNome());
            return dto;
        });
    }
}



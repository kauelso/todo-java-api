package com.example.todoapp.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecodedTokenDTO {
    private String username;

    private boolean isRemote;

    private boolean isAdmin;

    private String id;
}

package com.example.todoapp.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecodedTokenDTO {
    private String username;

    private boolean isRemote;

    private String id;
}

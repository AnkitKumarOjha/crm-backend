package com.crm.backend.dto;

import com.crm.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String name;
    private String email;
    private String password;
    private Role role;
}

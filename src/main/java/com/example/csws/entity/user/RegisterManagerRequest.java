package com.example.csws.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegisterManagerRequest {
    private String username;
    private String password;
    private String phone;
    private String university;
    private String department;

    public UserDto toUserDto() {
        return UserDto.builder()
                .username(username)
                .password(password)
                .phone(phone)
                .build();
    }
}

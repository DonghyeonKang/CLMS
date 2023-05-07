package com.example.csws.entity.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegisterStudentRequest {
    private String username;
    private String password;
    private String universityId;
    private String departmentId;

    public UserDto toUserDto() {
        return UserDto.builder()
                .username(username)
                .password(password)
                .departmentId(Integer.parseInt(departmentId))
                .universityId(Integer.parseInt(universityId))
                .build();
    }
}

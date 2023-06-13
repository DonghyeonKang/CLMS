package com.example.csws.entity.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

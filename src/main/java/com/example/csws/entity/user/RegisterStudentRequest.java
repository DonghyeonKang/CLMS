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
    private int no;
    private String name;

    public UserDto toUserDto() {
        return UserDto.builder()
                .username(username)
                .name(name)
                .no(no)
                .password(password)
                .departmentId(Long.parseLong(departmentId))
                .universityId(Integer.parseInt(universityId))
                .build();
    }
}

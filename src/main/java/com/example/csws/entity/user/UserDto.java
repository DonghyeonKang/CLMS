package com.example.csws.entity.user;

import com.example.csws.entity.department.Department;
import com.example.csws.entity.university.University;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor  // 매개변수가 없는 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자
public class UserDto {

    private int id;
    private String username;
    private String password;
    private String role;
    private String phone;
    private int universityId;
    private int departmentId;

    public User toUserEntity(Department department, University university) {
        return User.builder()
                .username(username)
                .password(password)
                .roles(role)
                .department(department)
                .university(university)
                .build();
    }

    public User toManagerEntity(Department department, University university) {
        return User.builder()
                .username(username)
                .password(password)
                .roles(role)
                .phone(phone)
                .department(department)
                .university(university)
                .build();
    }
}

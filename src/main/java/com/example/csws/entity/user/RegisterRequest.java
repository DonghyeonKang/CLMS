package com.example.csws.entity.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {  // 회원 가입 요청으로 들어오는 데이터를 넣을 DTO
    @Email
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public UserDto toUserDto() {
        return UserDto.builder()
                .username(username)
                .password(password)
                .build();
    }
}

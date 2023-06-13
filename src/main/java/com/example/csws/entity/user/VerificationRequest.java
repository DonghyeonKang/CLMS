package com.example.csws.entity.user;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequest {
    private String authNumber;
    @Email
    private String email;
}

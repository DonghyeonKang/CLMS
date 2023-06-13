package com.example.csws.entity.mail;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {
    private String email;
    private String authNum;

    public Mail toEntity() {
        return Mail.builder()
                .email(email)
                .authNum(authNum)
                .build();
    }
}

package com.example.csws.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ManagerAuthorityResponse {
    private String username;
    private String university;
    private String department;
    private String phone;
    private String status;
}

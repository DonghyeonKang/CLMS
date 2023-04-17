package com.example.csws.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDto {
    private String username;
    private String status;

    public Approval toApproval(Long userId) {
        return Approval.builder()
                .userId(userId)
                .status(status)
                .build();
    }
}

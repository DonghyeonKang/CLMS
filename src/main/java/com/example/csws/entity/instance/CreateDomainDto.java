package com.example.csws.entity.instance;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDomainDto {
    private boolean success;
    private String domainName;

    public boolean getSuccess() {
        return this.success;
    }
}

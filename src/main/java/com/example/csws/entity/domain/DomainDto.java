package com.example.csws.entity.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomainDto {
    private String name;
    private int instanceId;

    public Domain toEntity() {
        return Domain.builder()
                .name(name)
                .instanceId(instanceId)
                .build();
    }
}

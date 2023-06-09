package com.example.csws.entity.domain;

import com.example.csws.entity.instance.Instance;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomainDto {
    private String name;
    private Integer instanceId;

    public Domain toEntity(Instance instance) {
        return Domain.builder()
                .name(name)
                .instance(instance)
                .build();
    }
}

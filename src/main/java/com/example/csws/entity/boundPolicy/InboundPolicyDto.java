package com.example.csws.entity.boundPolicy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundPolicyDto {

    private int id;
    private int port;
    private int instanceId;

    public InboundPolicy toEntity() {
        return InboundPolicy.builder()
                .id(id)
                .port(port)
                .instanceId(instanceId)
                .build();
    }

}

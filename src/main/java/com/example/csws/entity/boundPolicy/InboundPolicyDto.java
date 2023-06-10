package com.example.csws.entity.boundPolicy;

import com.example.csws.entity.instance.Instance;
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
    private Integer hostPort;
    private int instancePort;
    private int instanceId;

    public InboundPolicy toCreatingEntity(Instance instance) {
        return InboundPolicy.builder()
                .instancePort(instancePort)
                .instance(instance)
                .build();
    }

    public InboundPolicy toUpdatingEntity(Instance instance) {
        return InboundPolicy.builder()
                .id(id)
                .instancePort(instancePort)
                .hostPort(hostPort)
                .instance(instance)
                .build();
    }
}

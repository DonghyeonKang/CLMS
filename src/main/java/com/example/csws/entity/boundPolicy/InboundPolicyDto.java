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
    private int port;
    private int instanceId;

    public InboundPolicy toEntity(Instance instance) {
        return InboundPolicy.builder()
                .id(id)
                .port(port)
                .instance(instance)
                .build();
    }

}

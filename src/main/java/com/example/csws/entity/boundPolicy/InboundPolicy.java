package com.example.csws.entity.boundPolicy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int port;
    private int instanceId;

    public InboundPolicyDto toDto() {
        return InboundPolicyDto.builder()
                .id(id)
                .port(port)
                .instanceId(instanceId)
                .build();
    }

}

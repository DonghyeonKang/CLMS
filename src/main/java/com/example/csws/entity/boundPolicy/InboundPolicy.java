package com.example.csws.entity.boundPolicy;

import com.example.csws.entity.instance.Instance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @ManyToOne(targetEntity = Instance.class)
    @JoinColumn(name = "instanceId")
    private Instance instance;

    public InboundPolicyDto toDto() {
        return InboundPolicyDto.builder()
                .id(id)
                .port(port)
                .instanceId(instance.getId())
                .build();
    }

}

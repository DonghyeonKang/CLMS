package com.example.csws.entity.instance;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int code;
    private String state;
    private Double storage;
    private String address;
    private int port;
    @Column(name = "keyname")
    private String keyName;
    private String os;
    private Timestamp created;
    @Column(name = "userid")
    private String userId;
    @Column(name = "serverid")
    private String serverId;

    public InstanceDto toDto() {
        return InstanceDto.builder()
                .id(id)
                .name(name)
                .code(code)
                .state(state)
                .storage(storage)
                .address(address)
                .port(port)
                .keyName(keyName)
                .os(os)
                .created(created)
                .userId(userId)
                .serverId(serverId)
                .build();
    }

}

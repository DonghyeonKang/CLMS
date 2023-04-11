package com.example.csws.entity.instance;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstanceDto {

    private int id;
    private String name;
    private int code;
    private String state;
    private Double storage;
    private String address;
    private int port;
    private String keyName;
    private String os;
    private Date created;
    private String userId;
    private String serverId;

    public Instance toEntity() {
        return Instance.builder()
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

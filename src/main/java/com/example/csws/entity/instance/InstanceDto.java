package com.example.csws.entity.instance;

import com.example.csws.entity.server.Server;
import com.example.csws.entity.user.User;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstanceDto {

    private int instanceId;
    private String name;
    private int code;
    private String state;
    private Double storage;
    private String address;
    private int port;
    private String keyName;
    private String os;
    private Timestamp created;
    private Long userId;
    private Long serverId;
    private String domainName;

    public Instance toEntity(User user, Server server) {
        return Instance.builder()
                .id(instanceId)
                .name(name)
                .code(code)
                .state(state)
                .storage(storage)
                .address(address)
                .port(port)
                .keyName(keyName)
                .os(os)
                .created(created)
                .user(user)
                .server(server)
                .build();
    }
}

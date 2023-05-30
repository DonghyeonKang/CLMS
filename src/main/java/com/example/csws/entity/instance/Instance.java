package com.example.csws.entity.instance;

import com.example.csws.entity.server.Server;
import com.example.csws.entity.user.User;
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
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(targetEntity = Server.class)
    @JoinColumn(name = "server_id")
    private Server server;

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
                .userId(user.getId())
                .serverId(server.getId())
                .build();
    }

    public void updateInstancePort(int port) {
        this.port = port;
    }
}

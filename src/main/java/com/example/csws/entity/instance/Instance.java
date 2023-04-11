package com.example.csws.entity.instance;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    private Date created;
    @Column(name = "userid")
    private String userId;
    @Column(name = "serverid")
    private String serverId;

    @Builder
    public Instance(int id, String name, int code, String state, Double storage, String address, int port, String keyName, String os, Date created, String userId, String serverId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.state = state;
        this.storage = storage;
        this.address = address;
        this.port = port;
        this.keyName = keyName;
        this.os = os;
        this.created = created;
        this.userId = userId;
        this.serverId = serverId;
    }

}

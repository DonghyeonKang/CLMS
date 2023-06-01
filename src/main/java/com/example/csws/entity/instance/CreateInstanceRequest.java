package com.example.csws.entity.instance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateInstanceRequest {
    private String name;
    private String storage;
    private String address;
    private String port;
    private String keyName;
    private String os;
    private String serverId;
}

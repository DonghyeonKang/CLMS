package com.example.csws.entity.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServerRegisterRequest {
    private String ipv4;
    private String serverName;
    private String serverUsername;
    private String departmentId;

    public ServerDto toServerDto() {
        return ServerDto.builder()
                .ipv4(ipv4)
                .serverName(serverName)
                .serverUsername(serverUsername)
                .departmentId(Long.parseLong(departmentId))
                .build();
    }
}

package com.example.csws.entity.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServerRegisterRequest {
    private String ipv4;
    private String serverName;
    private String serverUsername;

    public ServerDto toServerDto() {
        return ServerDto.builder()
                .ipv4(ipv4)
                .serverName(serverName)
                .serverUsername(serverUsername)
                .build();
    }
}

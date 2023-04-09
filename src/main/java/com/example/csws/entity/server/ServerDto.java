package com.example.csws.entity.server;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerDto {
    private String ipv4;
    private String serverName;
    private String serverUsername;
    public Server toEntity() {
        return Server.builder()
                .name(serverName)
                .ipv4(ipv4)
                .serverUsername(serverUsername)
                .build();
    }
}

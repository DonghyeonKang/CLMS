package com.example.csws.entity.server;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerListResponse {

    private int id;
    private String name;
    private String ipv4;
    private String hostname;
}

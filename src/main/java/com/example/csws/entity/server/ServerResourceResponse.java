package com.example.csws.entity.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerResourceResponse {
    private String ram;
    private String disk;
    private String connection;
}
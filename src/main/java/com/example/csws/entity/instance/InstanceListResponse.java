package com.example.csws.entity.instance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstanceListResponse {
    private int instanceId;
    private String name;
    private String userName;
}

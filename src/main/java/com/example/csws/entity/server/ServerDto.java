package com.example.csws.entity.server;

import com.example.csws.entity.department.Department;
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
    private int departmentId;
    public Server toEntity(Department department) {
        return Server.builder()
                .name(serverName)
                .ipv4(ipv4)
                .serverUsername(serverUsername)
                .department(department)
                .build();
    }
}

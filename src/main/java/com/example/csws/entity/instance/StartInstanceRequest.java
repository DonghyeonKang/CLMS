package com.example.csws.entity.instance;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StartInstanceRequest {
    private String instanceId;
}

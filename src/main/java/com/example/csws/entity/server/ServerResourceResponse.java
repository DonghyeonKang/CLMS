package com.example.csws.entity.server;

import lombok.*;
import org.json.simple.JSONObject;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerResourceResponse {

    List<JSONObject> resultList;
    Boolean success;
}
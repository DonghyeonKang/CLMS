package com.example.csws.common.shRunner;

import com.example.csws.entity.server.ServerResourceResponse;
import lombok.*;
import org.json.simple.JSONObject;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParserResponseDto {

    List<JSONObject> resultList;
    Boolean success;

    public ServerResourceResponse toDto() {
        return ServerResourceResponse.builder()
                .resultList(resultList)
                .success(success)
                .build();
    }
}

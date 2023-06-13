package com.example.csws.common.shRunner;

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

}

package com.example.csws.entity.lecture;

import lombok.Getter;

@Getter
public class CreateLectureRequest {
    private String lectureName;
    private String introducing;
    private Long serverId;
    private Long departmentId;
}

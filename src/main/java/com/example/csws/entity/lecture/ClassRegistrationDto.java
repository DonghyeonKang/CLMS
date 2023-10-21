package com.example.csws.entity.lecture;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassRegistrationDto {
    private Long id;
    private Long userId;
    private Long lectureId;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getLectureId() {
        return lectureId;
    }
}

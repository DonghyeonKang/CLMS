package com.example.csws.entity.lecture;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private String name;
    private Long studentId;
}

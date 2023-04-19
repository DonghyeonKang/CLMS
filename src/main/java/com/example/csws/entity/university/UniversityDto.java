package com.example.csws.entity.university;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDto {

    private int id;
    private String name;

    public University toEntity() {
        return University.builder()
                .id(id)
                .name(name)
                .build();
    }
}

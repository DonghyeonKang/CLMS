package com.example.csws.entity.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private int id;
    private String name;
    private int universityId;

    public Department toEntity() {
        return Department.builder()
                .id(id)
                .name(name)
                .universityId(universityId)
                .build();
    }

}

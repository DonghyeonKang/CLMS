package com.example.csws.entity.department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "universityid")
    private int universityId;

    public DepartmentDto toDto() {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(id);
        dto.setName(name);
        dto.setUniversityId(universityId);
        return dto;
    }

}

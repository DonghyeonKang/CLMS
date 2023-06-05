package com.example.csws.entity.university;

import com.example.csws.entity.department.Department;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public UniversityDto toDto() {
        UniversityDto dto = new UniversityDto();
        dto.setId(id);
        dto.setName(name);
        return dto;
    }
}

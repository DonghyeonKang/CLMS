package com.example.csws.entity.lecture;


import com.example.csws.entity.department.Department;
import com.example.csws.entity.server.Server;
import com.example.csws.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lectureName;
    private String introducing;
    @ManyToOne(targetEntity = Server.class)
    @JoinColumn(name = "server_id")
    private Server server;

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "department_id")
    private Department department;

    public LectureDto toDto() {
        return LectureDto.builder()
                .lectureName(lectureName)
                .introducing(introducing)
                .id(id)
                .build();
    }
}

package com.example.csws.entity.lecture;

import com.example.csws.entity.department.Department;
import com.example.csws.entity.server.Server;
import com.example.csws.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LectureDto {
    private Long id;
    private String lectureName;
    private String introducing;
    private Long serverId;

    public Lecture toEntity(Server server, Department department) {
        return Lecture.builder()
                .introducing(introducing)
                .lectureName(lectureName)
                .server(server)
                .department(department)
                .build();
    }
}

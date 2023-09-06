package com.example.csws.service.lecture;

import com.example.csws.entity.lecture.CreateLectureRequest;
import com.example.csws.entity.lecture.Lecture;
import com.example.csws.entity.server.Server;
import com.example.csws.repository.lecture.LectureRepository;
import com.example.csws.repository.server.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService{
    private final LectureRepository lectureRepository;
    private final ServerRepository serverRepository;

    @Override
    public void createLecture(CreateLectureRequest createLectureRequest) {
        Server server = serverRepository.getReferenceById(createLectureRequest.getServerId());

        Lecture lecture = Lecture.builder()
                .lectureName(createLectureRequest.getLectureName())
                .introducing(createLectureRequest.getIntroducing())
                .server(server)
                .build();
        lectureRepository.save(lecture);
    }
}

package com.example.csws.repository.lecture;

import com.example.csws.entity.lecture.LectureUser;
import com.example.csws.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureUserRepository  extends JpaRepository<LectureUser, Long> {
    public Optional<LectureUser> findById(Long id);

    public LectureUser save(LectureUser lectureUser);
    public List<LectureUser> findAllByLectureId(Long lectureId);
}

package com.example.csws.repository.lecture;

import com.example.csws.entity.lecture.Lecture;
import com.example.csws.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    public Optional<Lecture> findById(Long lectureId);
    public List<Lecture> findAllByDepartmentId(Long departmentId);
    public void deleteById(Long lectureId);
}

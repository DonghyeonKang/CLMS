package com.example.csws.repository.lecture;

import com.example.csws.entity.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    public Optional<Lecture> findByUserId(Long userId);
    public Optional<Lecture> findById(Long userId);

}

package com.example.csws.repository.lecture;

import com.example.csws.entity.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLectureRepository extends JpaRepository<Lecture, Long>, LectureRepository {
}

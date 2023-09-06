package com.example.csws.repository.lecture;

import com.example.csws.entity.lecture.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaNoticeRepository extends JpaRepository<Notice, Long>, NoticeRepository {
    public List<Notice> findAllByLectureId(Long lectureId);
    public Optional<Notice> findById(Long noticeId);
    public Notice save(Notice notice);
    public void deleteById(Long noticeId);
}

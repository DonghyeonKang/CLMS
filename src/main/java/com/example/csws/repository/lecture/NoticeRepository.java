package com.example.csws.repository.lecture;

import com.example.csws.entity.lecture.Notice;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {
    public List<Notice> findAllByLectureId(Long lectureId);
    public Optional<Notice> findById(Long noticeId);
    public Notice save(Notice notice);
    public void deleteById(Long noticeId);
}

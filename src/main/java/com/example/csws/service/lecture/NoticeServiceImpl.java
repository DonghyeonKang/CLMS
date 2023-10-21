package com.example.csws.service.lecture;

import com.example.csws.common.exception.EntityNotFoundException;
import com.example.csws.common.exception.ErrorCode;
import com.example.csws.entity.lecture.Lecture;
import com.example.csws.entity.lecture.Notice;
import com.example.csws.entity.lecture.NoticeDto;
import com.example.csws.repository.lecture.LectureRepository;
import com.example.csws.repository.lecture.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final LectureRepository lectureRepository;

    public List<NoticeDto> getNoticeList(Long lectureId) {
        List<Notice> data = noticeRepository.findAllByLectureId(lectureId);

        List<NoticeDto> result = data.stream()
                .map(m -> m.toDto())
                .collect(Collectors.toList());

        return result;
    }
    public NoticeDto postingNotice(NoticeDto noticeDto) {
        // 엔티티 생성 시 필요한 주소 값
        Lecture lecture = lectureRepository.getReferenceById(noticeDto.getLectureId());

        // 엔티티 생성
        Notice notice = Notice.builder()
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .createAt(noticeDto.getCreateAt())
                .lecture(lecture)
                .build();

        return noticeRepository.save(notice).toDto();
    }
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }
}

package com.example.csws.service.lecture;

import com.example.csws.entity.lecture.NoticeDto;

import java.util.List;

public interface NoticeService {
    public List<NoticeDto> getNoticeList(Long lectureId);
    public NoticeDto postingNotice(NoticeDto noticeDto);
    public void deleteNotice(Long noticeId);
}

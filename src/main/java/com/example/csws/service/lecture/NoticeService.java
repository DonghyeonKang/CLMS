package com.example.csws.service.lecture;


import com.example.csws.entity.lecture.Notice;
import com.example.csws.entity.lecture.NoticeDto;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    public List<NoticeDto> getNoticeList(Long lectureId);
    public NoticeDto getNoticeDetail(Long noticeId);
    public void postingNotice(NoticeDto noticeDto);
    public void deleteNotice(Long noticeId);
}

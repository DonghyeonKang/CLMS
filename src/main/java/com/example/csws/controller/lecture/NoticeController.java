package com.example.csws.controller.lecture;

import com.example.csws.entity.lecture.Notice;
import com.example.csws.entity.lecture.NoticeDto;
import com.example.csws.entity.lecture.PostingNoticeRequest;
import com.example.csws.service.lecture.NoticeService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/list")
    public JSONObject getNoticeList(@RequestParam(value = "id") Long lectureId) {
        // get notice list
        List<NoticeDto> result;
        result = noticeService.getNoticeList(lectureId);

        // result to json
        JSONObject obj = new JSONObject();
        obj.put("notices", result);
        return obj;
    }

    @PostMapping()
    public NoticeDto postingNotice(@RequestBody PostingNoticeRequest postingNoticeRequest) {
        // request to dto
        NoticeDto noticeDto = NoticeDto.builder()
                .title(postingNoticeRequest.getTitle())
                .content(postingNoticeRequest.getContent())
                .lectureId(postingNoticeRequest.getLectureId())
                .createAt(postingNoticeRequest.getCreateAt())
                .build();

        // create notice
        return noticeService.postingNotice(noticeDto);
    }

    @DeleteMapping()
    public void deleteNotice(@RequestParam(value = "id") Long noticeId) {
        noticeService.deleteNotice(noticeId);
    }
}
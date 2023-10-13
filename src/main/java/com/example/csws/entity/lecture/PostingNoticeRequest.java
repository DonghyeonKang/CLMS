package com.example.csws.entity.lecture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostingNoticeRequest {
    private String title;
    private String content;
    private Long lectureId;
    private String createAt;
}

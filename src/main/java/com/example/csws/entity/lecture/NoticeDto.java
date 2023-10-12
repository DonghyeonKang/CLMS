package com.example.csws.entity.lecture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
    private Long noticeId;
    private String title;
    private String content;
    private Long lectureId;

    Notice toEntity(Lecture lecture) {
        return Notice.builder()
                .title(title)
                .content(content)
                .lecture(lecture)
                .build();
    }
}

package com.example.csws.entity.lecture;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String createAt;

    @ManyToOne(targetEntity = Lecture.class)
    @JoinColumn(name = "Lecture_id")
    private Lecture lecture;

    public NoticeDto toDto() {
        return NoticeDto.builder()
                .noticeId(id)
                .title(title)
                .content(content)
                .createAt(createAt)
                .lectureId(lecture.getId())
                .build();
    }
}
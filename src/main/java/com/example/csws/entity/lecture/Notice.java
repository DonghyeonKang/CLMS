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

    @ManyToOne(targetEntity = Lecture.class)
    @JoinColumn(name = "Lecture_id")
    private Lecture lecture;

    public NoticeDto toDto() {
        return NoticeDto.builder()
                .title(title)
                .content(content)
                .lectureId(lecture.getId())
                .build();
    }
}

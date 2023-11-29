package com.example.csws.repository.lecture;

import com.example.csws.entity.lecture.LectureUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LectureUserRepository  extends JpaRepository<LectureUser, Long> {
    public Optional<LectureUser> findById(Long id);

    public LectureUser save(LectureUser lectureUser);

    @Query(value = "select * from lecture_user where lecture_id=?1 and permit='waiting'", nativeQuery = true)
    public List<LectureUser> findAllByWaitingUserId(Long lectureId);

    @Query(value = "select * from lecture_user where lecture_id=?1 and permit='permit'", nativeQuery = true)
    public List<LectureUser> findAllByPermittedUserId(Long lectureId);

    @Query(value = "select * from lecture_user where user_id=?1 and permit='permit'", nativeQuery = true)
    public List<LectureUser> findAllByUserId(Long userId);
}

//public List<School> find By Ranking Between And Name Containing(int startRanking, int endRanking, String name); // where ranking between
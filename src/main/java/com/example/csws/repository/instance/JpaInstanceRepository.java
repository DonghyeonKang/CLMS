package com.example.csws.repository.instance;

import com.example.csws.entity.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaInstanceRepository extends JpaRepository<Instance, Long>, InstanceRepository {
    @Query(value = "select id from instance where user_id=?1 and lecture_id=?2", nativeQuery = true)
    public Optional<Integer> findIdByUserIdAndLectureId(Long userid, Long lectureId);
}
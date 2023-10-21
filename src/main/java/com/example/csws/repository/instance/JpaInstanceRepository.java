package com.example.csws.repository.instance;

import com.example.csws.entity.instance.Instance;
import com.example.csws.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaInstanceRepository extends JpaRepository<Instance, Long>, InstanceRepository {
    @Query(value = "select id from instance where user_id=?1 and lecture_id=?2", nativeQuery = true)
    public int findIdByUserIdAndLectureId(Long userid, Long lectureId);
}
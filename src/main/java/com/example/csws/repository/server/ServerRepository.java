package com.example.csws.repository.server;

import com.example.csws.entity.lecture.Lecture;
import com.example.csws.entity.server.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    // 학과 id 로 서버 리스트 조회
    public List<Server> findAllByDepartmentId(Long departmentId);

    public Optional<Long> findServerIdByDepartmentId(int departmentId);

    // 서버 등록
    public Server save(Server server);

    // 서버 삭제
    public void deleteById(int serverId);

    // 서버 id로 조회
    public Optional<Server> findById(int serverId);
}


package com.example.csws.repository.server;

import com.example.csws.entity.server.Server;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository {
    // 학과 id 로 서버 리스트 조회
    public List<Server> findAllByDepartmentId(int departmentId);

    // 서버 등록
    public Server save(Server server);

    // 서버 삭제
    public void deleteById(int serverId);
}


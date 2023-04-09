package com.example.csws.repository.server;

import com.example.csws.entity.server.Server;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository {
    public List<Server> findAllByDepartmentId(int departmentId);
    public Server save(Server server);
}


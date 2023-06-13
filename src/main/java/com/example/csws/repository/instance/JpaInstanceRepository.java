package com.example.csws.repository.instance;

import com.example.csws.entity.instance.Instance;
import com.example.csws.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaInstanceRepository extends JpaRepository<Instance, Long>, InstanceRepository {
    public List<User> findAllUserByServerId(int serverId);
}
package com.example.csws.repository.user;

import com.example.csws.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<List<String>> findUsernameByDepartmentId(int departmentId);
    void deleteByUsername(String username);
}

package com.example.csws.repository.user;

import com.example.csws.entity.user.ManagerAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerAuthoritiesRepository extends JpaRepository<ManagerAuthority, Long> {
}

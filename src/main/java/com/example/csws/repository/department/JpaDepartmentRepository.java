package com.example.csws.repository.department;

import com.example.csws.entity.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaDepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepository {

    @Override
    List<Department> findAllByUniversityId(String universityId);

}

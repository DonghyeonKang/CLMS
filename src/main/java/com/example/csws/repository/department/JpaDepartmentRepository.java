package com.example.csws.repository.department;

import com.example.csws.entity.department.Department;
import com.example.csws.entity.university.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaDepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepository {

    // universityId 로 모든 학과 조회
    @Override
    List<Department> findAllByUniversity_Id(int universityId);
}

package com.example.csws.repository.department;

import com.example.csws.entity.department.Department;
import com.example.csws.entity.university.University;

import java.util.List;

public interface DepartmentRepository {

    // universityId 로 모든 학과 조회
    public List<Department> findAllByUniversity_Id(int universityId);
}

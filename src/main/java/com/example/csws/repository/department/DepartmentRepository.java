package com.example.csws.repository.department;

import com.example.csws.entity.department.Department;

import java.util.List;

public interface DepartmentRepository {

    public List<Department> findAllByUniversityId(String universityId);

}

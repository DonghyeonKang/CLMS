package com.example.csws.service.department;

import com.example.csws.entity.department.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    // universityId 로 모든 학과 조회
    public List<DepartmentResponse> findAllDepartment(int universityId);
}

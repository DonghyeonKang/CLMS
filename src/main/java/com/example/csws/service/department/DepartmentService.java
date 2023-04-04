package com.example.csws.service.department;

import com.example.csws.entity.department.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    public List<DepartmentDto> findAllDepartment(String universityId);

}

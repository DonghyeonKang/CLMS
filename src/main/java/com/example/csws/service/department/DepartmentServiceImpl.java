package com.example.csws.service.department;

import com.example.csws.entity.department.Department;
import com.example.csws.entity.department.DepartmentDto;
import com.example.csws.repository.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> findAllDepartment(String universityId) {

        List<Department> entityList = departmentRepository.findAllByUniversityId(universityId);
        List<DepartmentDto> dtoList = new ArrayList<>();

        for (Department entity : entityList) {
            dtoList.add(entity.toDto());
        }

        return dtoList;
    }
}

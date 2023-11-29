package com.example.csws.service.department;

import com.example.csws.entity.department.Department;
import com.example.csws.entity.department.DepartmentResponse;
import com.example.csws.repository.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    // universityId 로 모든 학과 조회
    @Override
    public List<DepartmentResponse> findAllDepartment(int universityId) {

        List<Department> entityList = departmentRepository.findAllByUniversity_Id(universityId);
        List<DepartmentResponse> dtoList = new ArrayList<>();

        // entity List 를 dto List 로 변환
        for (Department entity : entityList) {
            dtoList.add(entity.toDto());
        }

        return dtoList;
    }
}

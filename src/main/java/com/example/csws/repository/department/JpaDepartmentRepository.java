package com.example.csws.repository.department;

import com.example.csws.entity.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaDepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepository {

    // universityId 로 모든 학과 조회
    @Override
    public List<Department> findAllByUniversity_Id(int universityId);

    // department 의 참조 값을 넘겨줌
    @Override
    public Department getReferenceById(Long departmentId);
}

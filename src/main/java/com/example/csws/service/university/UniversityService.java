package com.example.csws.service.university;

import com.example.csws.entity.university.University;
import com.example.csws.entity.university.UniversityDto;

import java.util.List;

public interface UniversityService {

    // 모든 학교 조회
    public List<UniversityDto> findAllUniversity();
    public University findById(int UniversityId);
}

package com.example.csws.service.university;

import com.example.csws.entity.university.University;
import com.example.csws.entity.university.UniversityDto;
import com.example.csws.repository.university.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService{

    private final UniversityRepository universityRepository;

    @Override
    public List<UniversityDto> findAllUniversity() {

        List<University> entityList = universityRepository.findAll();
        List<UniversityDto> dtoList = new ArrayList<>();

        for (University entity : entityList) {
            dtoList.add(entity.toDto());
        }

        return dtoList;
    }
}

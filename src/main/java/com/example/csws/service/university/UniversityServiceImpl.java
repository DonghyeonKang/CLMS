package com.example.csws.service.university;

import com.example.csws.entity.university.University;
import com.example.csws.entity.university.UniversityDto;
import com.example.csws.repository.university.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService{

    private UniversityRepository universityRepository;

    @Override
    public List<UniversityDto> findAllUniversity() {

        List<University> entityList = universityRepository.findAll();
        List<UniversityDto> dtoList = new ArrayList<>();

        for (University entity : entityList) {
            UniversityDto dto = new UniversityDto();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        }

        return dtoList;
    }
}

package com.example.csws.repository.university;

import com.example.csws.entity.university.University;

import java.util.List;

public interface UniversityRepository {

    public List<University> findAll();
    public University findById(int universityId);

    // university 의 참조값을 넘겨줌
    public University getReferenceById(int universityId);

}

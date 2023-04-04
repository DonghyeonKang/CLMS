package com.example.csws.repository.university;

import com.example.csws.entity.university.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaUniversityRepository extends JpaRepository<University, Long>, UniversityRepository {

    public List<University> findAll();
}

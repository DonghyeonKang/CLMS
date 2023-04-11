package com.example.csws.repository.instance;

import com.example.csws.entity.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInstanceRepository extends JpaRepository<Instance, String>, InstanceRepository {


}
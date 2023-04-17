package com.example.csws.repository.domain;

import com.example.csws.entity.domain.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaDomainRepository extends JpaRepository<Domain, Long>, DomainRepository {
    public Domain findByInstanceId(String instanceId);
    public Domain save(Domain domain);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE domain SET name = null WHERE instanceId = :id")
    public Domain delete(@Param(value="instanceId") int instanceId);
}

package com.example.csws.repository.domain;

import com.example.csws.entity.domain.Domain;
import org.springframework.data.repository.query.Param;

public interface DomainRepository {
    public Domain findByInstanceId(String instanceId);
    public Domain save(Domain domain);
    public Domain delete(@Param(value="instanceId") int instanceId);
}

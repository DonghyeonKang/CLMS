package com.example.csws.service.domain;

import com.example.csws.entity.domain.Domain;
import com.example.csws.entity.domain.DomainDto;

import java.util.List;

public interface DomainService {
    public Domain findByInstanceId(String instanceId);
    public Domain createDomain(DomainDto domainDto);
    public void deleteDomain(DomainDto domainDto);
}

package com.example.csws.service.domain;

import com.example.csws.entity.domain.Domain;
import com.example.csws.entity.domain.DomainDto;
import org.json.simple.JSONObject;


public interface DomainService {
    public JSONObject findByInstanceId(Integer instanceId);
    public DomainDto createDomain(DomainDto domainDto);
    public void deleteDomain(DomainDto domainDto);
}

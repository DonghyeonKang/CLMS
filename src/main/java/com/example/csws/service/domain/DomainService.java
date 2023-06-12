package com.example.csws.service.domain;

import com.example.csws.entity.domain.Domain;
import com.example.csws.entity.domain.DomainDto;
import com.example.csws.entity.instance.CreateDomainDto;
import org.json.simple.JSONObject;


public interface DomainService {
    public JSONObject findByInstanceId(Integer instanceId);
    public CreateDomainDto createDomain(DomainDto domainDto);
    public Boolean deleteDomain(DomainDto domainDto);
}

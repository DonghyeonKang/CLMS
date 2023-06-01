package com.example.csws.service.boundPolicy;

import com.example.csws.entity.boundPolicy.InboundPolicy;
import com.example.csws.entity.boundPolicy.InboundPolicyDto;

import java.util.List;

public interface InboundPolicyService {

    public List<InboundPolicyDto> findAllByInstanceId(int instanceId);

    public InboundPolicyDto save(InboundPolicy inboundPolicy);

    public List<InboundPolicyDto> saveAll(List<InboundPolicyDto> inboundPolicyList);

    public void delete(InboundPolicy inboundPolicy);

    public void deleteAll(List<InboundPolicy> inboundPolicyList);

}

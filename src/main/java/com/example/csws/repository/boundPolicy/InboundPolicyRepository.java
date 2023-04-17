package com.example.csws.repository.boundPolicy;

import com.example.csws.entity.boundPolicy.InboundPolicy;

import java.util.List;
import java.util.Optional;

public interface InboundPolicyRepository {

    public List<InboundPolicy> findAllByInstanceId(int instanceId);

    public Optional<InboundPolicy> save(InboundPolicy inboundPolicy);

    public List<InboundPolicy> saveAll(List<InboundPolicy> inboundPolicyList);

    public void delete(InboundPolicy inboundPolicy);

}

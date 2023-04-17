package com.example.csws.repository.boundPolicy;

import com.example.csws.entity.boundPolicy.InboundPolicy;

import java.util.List;
import java.util.Optional;

public interface InboundPolicyRepository {

    public List<InboundPolicy> findAllByInstanceId(String instanceId);

    public Optional<InboundPolicy> save(InboundPolicy inboundPolicy);

    public void delete(InboundPolicy inboundPolicy);

}

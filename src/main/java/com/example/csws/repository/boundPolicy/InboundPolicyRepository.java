package com.example.csws.repository.boundPolicy;

import com.example.csws.entity.boundPolicy.InboundPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InboundPolicyRepository extends JpaRepository<InboundPolicy, Long> {

    List<InboundPolicy> findAllByInstanceIdIn(List<Integer> idList);

//    InboundPolicy save(InboundPolicy inboundPolicy);
//
//    List<InboundPolicy> saveAll(List<InboundPolicy> inboundPolicyList);
//
//    void delete(InboundPolicy inboundPolicy);

    void deleteAllByIdIn(List<Long> idList);

}

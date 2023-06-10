package com.example.csws.repository.boundPolicy;

import com.example.csws.entity.boundPolicy.InboundPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InboundPolicyRepository extends JpaRepository<InboundPolicy, Integer> {

    List<InboundPolicy> findAllByInstanceIdIn(List<Integer> idList);

    void deleteAllByIdIn(List<Long> idList);

}

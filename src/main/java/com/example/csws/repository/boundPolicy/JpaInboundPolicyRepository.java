package com.example.csws.repository.boundPolicy;

import com.example.csws.entity.boundPolicy.InboundPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInboundPolicyRepository extends JpaRepository<InboundPolicy, Long>{
}

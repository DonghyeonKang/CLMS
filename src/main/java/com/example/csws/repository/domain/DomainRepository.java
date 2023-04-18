package com.example.csws.repository.domain;

import com.example.csws.entity.domain.Domain;
import org.springframework.data.repository.query.Param;

public interface DomainRepository {
    // instanceId 로 domain 조회
    public Domain findByInstanceId(String instanceId);

    // domain 저장
    public Domain save(Domain domain);

    // domain 삭제
    public void deleteById(int instanceId);
}
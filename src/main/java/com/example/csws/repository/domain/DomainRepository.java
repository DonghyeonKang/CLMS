package com.example.csws.repository.domain;

import com.example.csws.entity.domain.Domain;

import java.util.Optional;

public interface DomainRepository {
    // instanceId 로 domain 조회
    public Optional<Domain> findByInstanceId(Integer instanceId);

    // domain 저장
    public Domain save(Domain domain);

    // domain 삭제
    public void deleteByInstanceId(Integer instanceId);
}
package com.example.csws.repository.domain;

import com.example.csws.entity.domain.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaDomainRepository extends JpaRepository<Domain, Integer>, DomainRepository {
    // instanceId 로 domain 조회
    public Optional<Domain> findByInstanceId(String instanceId);

    // 도메인 저장
    public Domain save(Domain domain);

    // 도메인 삭제
    public void delete(Domain domain);
}

package com.example.csws.service.domain;

import com.example.csws.entity.domain.Domain;
import com.example.csws.entity.domain.DomainDto;
import com.example.csws.repository.domain.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor    // private field 를 생성자 주입 방식으로 DI
public class DomainServiceImpl implements DomainService {
    private DomainRepository domainRepository;

    // instanceId 로 domain 조회
    public Domain findByInstanceId(String instanceId) {
        return domainRepository.findByInstanceId(instanceId);
    }

    public Domain createDomain(DomainDto domainDto) {
        Domain domain = domainDto.toEntity();   // 받은 dto를 entity로 바꿈
        return domainRepository.save(domain);   // 저장함
    }
    public void deleteDomain(DomainDto domainDto) {
        domainRepository.deleteById(domainDto.getInstanceId());
    }
}

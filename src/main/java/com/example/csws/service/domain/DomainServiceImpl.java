package com.example.csws.service.domain;

import com.example.csws.entity.domain.Domain;
import com.example.csws.entity.domain.DomainDto;
import com.example.csws.entity.instance.Instance;
import com.example.csws.repository.domain.DomainRepository;
import com.example.csws.repository.instance.InstanceRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor    // private field 를 생성자 주입 방식으로 DI
public class DomainServiceImpl implements DomainService {
    private final DomainRepository domainRepository;
    private final InstanceRepository instanceRepository;

    // instanceId 로 domain 조회
    public JSONObject findByInstanceId(Integer instanceId) {
        // 도메인 조회
        Optional<Domain> domain = domainRepository.findByInstanceId(instanceId);

        // json 객체 생성
        JSONObject obj = new JSONObject();
        if(domain.isEmpty()) {  // 조회된 도메인이 null 이라면,
            obj.put("domainName", null);
        } else {    // 조회된 도메인이 존재한다면
            obj.put("domainName", domain.get().getName());
        }

        return obj;
    }

    public DomainDto createDomain(DomainDto domainDto) {
        // instance 의 참조값 가져오기
        Instance instance = instanceRepository.getReferenceById(domainDto.getInstanceId());

        // 저장
        Domain domain = domainRepository.save(domainDto.toEntity(instance));
        return DomainDto.builder()
                .name(domain.getName())
                .build();
    }
    public void deleteDomain(DomainDto domainDto) {
        domainRepository.deleteById(domainDto.getInstanceId());
    }
}

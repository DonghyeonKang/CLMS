package com.example.csws.service.domain;

import com.example.csws.common.shRunner.ShParser;
import com.example.csws.common.shRunner.ShRunner;
import com.example.csws.entity.domain.Domain;
import com.example.csws.entity.domain.DomainDto;
import com.example.csws.entity.instance.CreateDomainDto;
import com.example.csws.entity.instance.Instance;
import com.example.csws.entity.server.Server;
import com.example.csws.repository.domain.DomainRepository;
import com.example.csws.repository.instance.InstanceRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor    // private field 를 생성자 주입 방식으로 DI
public class DomainServiceImpl implements DomainService {
    private final DomainRepository domainRepository;
    private final InstanceRepository instanceRepository;
    private final ShRunner shRunner;
    private final ShParser shParser;

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

    public CreateDomainDto createDomain(DomainDto domainDto) {
        // instance 의 참조값 가져오기
        Instance entity = instanceRepository.findById(domainDto.getInstanceId()).get();
        Server baseServer = entity.getServer();

        // 쉘 실행
        try {
            Map result = shRunner.execCommand("H_DeleteNginx.sh", baseServer.getServerUsername(),
                    baseServer.getIpv4(), domainDto.getName(), "80");

            // 성공 리턴
            if(shParser.isSuccess(result.get(1).toString())) {
                // 저장
                Domain domain = domainRepository.save(domainDto.toEntity(entity));
                return CreateDomainDto.builder()
                        .domainName(domain.getName())
                        .success(true)
                        .build();
            }

            // 실패 리턴
            return CreateDomainDto.builder()
                    .domainName("")
                    .success(false)
                    .build();
        } catch (Exception e) {
            System.out.println(e);
            // 실패 리턴
            return CreateDomainDto.builder()
                    .domainName("")
                    .success(false)
                    .build();
        }
    }

    @Transactional
    public Boolean deleteDomain(DomainDto domainDto) {
        // instance 의 참조값 가져오기
        Instance entity = instanceRepository.findById(domainDto.getInstanceId()).get();
        Server baseServer = entity.getServer();

        // 쉘 실행
        try {
            Map result = shRunner.execCommand("H_AddNginx.sh", baseServer.getServerUsername(),
                    baseServer.getIpv4(), domainDto.getName(), "80");

            // 성공 리턴
            if(shParser.isSuccess(result.get(1).toString())) {
                // 삭제
                domainRepository.deleteByInstanceId(domainDto.getInstanceId());
                return true;
            }

            // 실패 리턴
            return false;
        } catch (Exception e) {
            System.out.println(e);
            // 실패 리턴
            return false;
        }
    }
}

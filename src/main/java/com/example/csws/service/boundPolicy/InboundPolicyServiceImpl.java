package com.example.csws.service.boundPolicy;

import com.example.csws.common.shRunner.ShParser;
import com.example.csws.common.shRunner.ShRunner;
import com.example.csws.entity.boundPolicy.InboundPolicy;
import com.example.csws.entity.boundPolicy.InboundPolicyDto;
import com.example.csws.entity.instance.Instance;
import com.example.csws.entity.server.Server;
import com.example.csws.repository.boundPolicy.InboundPolicyRepository;
import com.example.csws.repository.instance.InstanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@RequiredArgsConstructor
public class InboundPolicyServiceImpl implements InboundPolicyService{

    private final InboundPolicyRepository inboundPolicyRepository;
    private final InstanceRepository instanceRepository;
    private final EntityManager entityManager;
    private final ShRunner shRunner;
    private final ShParser shParser;
    @Override
    public List<InboundPolicyDto> findAllByInstanceId(int instanceId) {

        List<Integer> idList = new ArrayList<>();
        idList.add(instanceId);
        List<InboundPolicy> entityList = inboundPolicyRepository.findAllByInstanceIdIn(idList);

        List<InboundPolicyDto> dtoList = new ArrayList<>();
        for (InboundPolicy entity : entityList) {
            dtoList.add(entity.toDto());
        }

        return dtoList;
    }

    @Override
    public InboundPolicyDto save(InboundPolicy inboundPolicy) {

        InboundPolicy entity = inboundPolicyRepository.save(inboundPolicy);

        return entity.toDto();
    }

    @Override
    public List<InboundPolicyDto> saveAll(List<InboundPolicyDto> dtoList) {
        // instance id 가져온 뒤 해당 인스턴스 참조값 받아오기
        int instanceId = dtoList.get(0).getInstanceId();
        Instance userInstance = instanceRepository.getReferenceById(instanceId);

        // 컨트롤러에서 받은 dto 리스트를 반복문으로 entity로 변환. 매번 인자로 instance 엔티티 넘기기
        List<InboundPolicy> newEntityList = new ArrayList<>();
        List<InboundPolicy> updateEntityList = new ArrayList<>();

        for (InboundPolicyDto dto : dtoList) {
            if(dto.getId() == -1) { // id 가 -1 인 dto 는 생성을 위한 엔티티로 변환
                newEntityList.add(dto.toCreatingEntity(userInstance));
            } else {    // id 가 -1 이 아니면 업데이트를 위한 엔티티로 변환
                updateEntityList.add(dto.toUpdatingEntity(userInstance));
            }
        }

        // 저장 --------------------
        List<InboundPolicyDto> responseDtoList = new ArrayList<>();
        if(newEntityList.size() != 0) {
            // 생성
            List<InboundPolicy> createdEntityList =  this.createNew(newEntityList);

            // 포트 업데이트
            for (InboundPolicy entity : createdEntityList) {
                // hostPort 지정
                entityManager.persist(entity);
                entity.updateHostPort();


                System.out.println("새로 생성된 hostport");
                System.out.println(entity.getHostPort());
                // 응답 리스트에 저장
                responseDtoList.add(entity.toDto());
            }


            // 쉘 실행
            Instance entity = instanceRepository.findById(instanceId).get();
            Server baseServer = entity.getServer();
            String containerName = entity.getName() + entity.getId();
            String newPorts = "";

            for (InboundPolicyDto inboundPolicyDto: responseDtoList) {
                try {
                    Map result = shRunner.execCommand("AddInbound.sh", baseServer.getServerUsername(),
                            baseServer.getIpv4(), containerName, inboundPolicyDto.getHostPort() + ":" + inboundPolicyDto.getInstancePort(), entity.getStorage().toString(), entity.getOs());

                    shParser.isSuccess(result.get(1).toString());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }


        }

        // 업데이트 --------------------
        if(updateEntityList.size() != 0) {
            for (InboundPolicy entity : updateEntityList) {
                // 엔티티 조회
                System.out.println("엔티티 조회");
                System.out.println(entity.getId());
                InboundPolicy inboundPolicy = inboundPolicyRepository.findById(entity.getId()).get();

                // 영속성 지정
                entityManager.persist(inboundPolicy);

                // 업데이트
                inboundPolicy.updateAllPort(entity.getHostPort(), entity.getInstancePort());

                // 응답 리스트 저장
                responseDtoList.add(entity.toDto());
            }
        }

        return responseDtoList;
    }

    private List<InboundPolicy> createNew(List<InboundPolicy> newEntityList) {
        System.out.println("createNew 진입");
        System.out.println(newEntityList.get(0).getInstancePort());
        List<InboundPolicy> createdEntityList = inboundPolicyRepository.saveAll(newEntityList);
        return createdEntityList;
    }

    @Override
    public void delete(InboundPolicy inboundPolicy) {
        inboundPolicyRepository.delete(inboundPolicy);
    }

    @Override
    public void deleteAll(List<InboundPolicy> inboundPolicyList) {

        List<Long> idList = new ArrayList<>();
        for (InboundPolicy entity : inboundPolicyList) {
            idList.add((long) entity.getId());
        }

        inboundPolicyRepository.deleteAllByIdIn(idList);
    }
}

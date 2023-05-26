package com.example.csws.service.boundPolicy;

import com.example.csws.entity.boundPolicy.InboundPolicy;
import com.example.csws.entity.boundPolicy.InboundPolicyDto;
import com.example.csws.entity.instance.Instance;
import com.example.csws.repository.boundPolicy.InboundPolicyRepository;
import com.example.csws.repository.instance.InstanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InboundPolicyServiceImpl implements InboundPolicyService{

    private final InboundPolicyRepository inboundPolicyRepository;
    private final InstanceRepository instanceRepository;

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
        List<InboundPolicy> entityList = new ArrayList<>();
        for (InboundPolicyDto dto : dtoList) {
            entityList.add(dto.toEntity(userInstance));
        }
        
        // 저장 후 반환된 값을 dto로 다시 변환해서 컨트롤러에 반환
        List<InboundPolicy> savedEntityList = inboundPolicyRepository.saveAll(entityList);
        List<InboundPolicyDto> savedDtoList = new ArrayList<>();

        for (InboundPolicy entity : savedEntityList) {
            savedDtoList.add(entity.toDto());
        }

        return savedDtoList;
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

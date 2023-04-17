package com.example.csws.service.boundPolicy;

import com.example.csws.entity.boundPolicy.InboundPolicy;
import com.example.csws.entity.boundPolicy.InboundPolicyDto;
import com.example.csws.repository.boundPolicy.InboundPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InboundPolicyServiceImpl implements InboundPolicyService{

    private final InboundPolicyRepository inboundPolicyRepository;

    @Override
    public List<InboundPolicyDto> findAllByInstanceId(int instanceId) {

        List<Integer> idList = new ArrayList<>();
        idList.add(instanceId);
        List<InboundPolicy> entityList = inboundPolicyRepository.findAllByInstanceId(idList);

        List<InboundPolicyDto> dtoList = new ArrayList<>();
        for (InboundPolicy entity : entityList) {
            dtoList.add(entity.toDto());
        }

        return dtoList;
    }

    @Override
    public InboundPolicyDto save(InboundPolicy inboundPolicy) {

        InboundPolicy entity = inboundPolicyRepository.save(inboundPolicy).get();

        return entity.toDto();
    }

    @Override
    public List<InboundPolicyDto> saveAll(List<InboundPolicy> inboundPolicyList) {

        List<InboundPolicy> entityList = inboundPolicyRepository.saveAll(inboundPolicyList);
        List<InboundPolicyDto> dtoList = new ArrayList<>();

        for (InboundPolicy entity : entityList) {
            dtoList.add(entity.toDto());
        }

        return dtoList;
    }

    @Override
    public void delete(InboundPolicy inboundPolicy) {
        inboundPolicyRepository.delete(inboundPolicy);
    }

    @Override
    public void deleteAll(List<InboundPolicy> inboundPolicyList) {
        inboundPolicyRepository.deleteAll(inboundPolicyList);
    }
}

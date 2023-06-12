package com.example.csws.service.instance;

import com.example.csws.common.shRunner.ParserResponseDto;
import com.example.csws.entity.instance.InstanceDto;

import java.util.List;
import java.util.Optional;

public interface InstanceService {

    public String createInstance(InstanceDto instanceDto);
    public Optional<InstanceDto> findById(int instanceId);
    public List<InstanceDto> findAllByUserId(Long userId);
    public List<InstanceDto> findAllByServerId(int serverId);

    public Boolean startInstance(int instanceId);
    public Boolean stopInstance(int instanceId);
    public Boolean restartInstance(int instanceId);
    public Boolean deleteInstance(int instanceId);

    public String createKeyPair(String hostName, String keyPairName);

    public InstanceDto changeUserid(InstanceDto instanceDto);

    public ParserResponseDto checkContainerResource(String hostName, String hostIp, String containerName);

    public ParserResponseDto checkServerResource(String hostName, String hostIp);

    public ParserResponseDto printStatusforManager(String hostName, String hostIp);

    public ParserResponseDto printStatusforUser(String hostName, String hostIp, String username);

}

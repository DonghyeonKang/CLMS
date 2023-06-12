package com.example.csws.service.instance;

import com.example.csws.entity.instance.InstanceDto;

import java.util.List;
import java.util.Optional;

public interface InstanceService {

    public String createInstance(InstanceDto instanceDto);
    public Optional<InstanceDto> findById(int instanceId);
    public List<InstanceDto> findAllByUserId(Long userId);
    public List<InstanceDto> findAllByServerId(int serverId);

    public String startInstance(int instanceId, String username);
    public String stopInstance(int instanceId, String username);
    public String restartInstance(int instanceId, String username);
    public String deleteInstance(int instanceId, String username);

    public String createKeyPair(String hostName, String keyPairName);

    public InstanceDto changeUserid(InstanceDto instanceDto);

}

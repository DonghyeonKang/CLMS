package com.example.csws.service.instance;

import com.example.csws.entity.instance.InstanceDto;

import java.util.List;
import java.util.Optional;

public interface InstanceService {

    public String createInstance(InstanceDto instanceDto);
    public Optional<InstanceDto> findById(int instanceId);
    public List<InstanceDto> findAllByUserId(int userId);
    public List<InstanceDto> findAllByServerId(int serverId);

    public String startInstance(int instanceId);
    public String stopInstance(int instanceId);
    public String restartInstance(int instanceId);
    public String deleteInstance(int instanceId);

    public void createKeyPair(String username, int instanceId);

    public InstanceDto changeUserid(InstanceDto instanceDto);

}

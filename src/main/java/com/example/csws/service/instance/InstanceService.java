package com.example.csws.service.instance;

import com.example.csws.entity.instance.InstanceDto;

import java.util.List;
import java.util.Optional;

public interface InstanceService {

    public InstanceDto createInstance(InstanceDto instanceDto);
    public Optional<InstanceDto> findByUserid(int userId);
    public List<InstanceDto> findAllByUserid(int userId);
    public Optional<InstanceDto> findByServerid(int serverId);
    public List<InstanceDto> findAllByServerid(int serverId);

    public String startInstance(int instanceId);
    public String stopInstance(int instanceId);
    public String restartInstance(int instanceId);
    public String deleteInstance(int instanceId);

    public void createKeyPair(String username, int instanceId);

    public InstanceDto changeUserid(InstanceDto instanceDto);

}

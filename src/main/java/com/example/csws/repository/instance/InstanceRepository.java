package com.example.csws.repository.instance;

import com.example.csws.entity.instance.Instance;

import java.util.List;
import java.util.Optional;

public interface InstanceRepository {

    public Instance save(Instance instance);

    public Optional<Instance> findById(int instanceId);

    public List<Instance> findAllByUserId(int userId);

    public List<Instance> findAllByServerId(int serverId);

    public void deleteById(int instanceId);

}

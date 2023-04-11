package com.example.csws.repository.instance;

import com.example.csws.entity.instance.Instance;

import java.util.List;
import java.util.Optional;

public interface InstanceRepository {

    public Instance save(Instance instance);

    public Optional<Instance> findByUserid(int userId);

    public List<Instance> findAllByUserid(int userId);

    public Optional<Instance> findByServerid(int serverId);

    public List<Instance> findAllByServerid(int serverId);

    public void delete(Instance instance);

}

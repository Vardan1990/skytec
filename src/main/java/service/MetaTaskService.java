package service;


import entity.MetaTask;

import java.util.List;
import java.util.Optional;

public interface MetaTaskService {

    MetaTask save(MetaTask metaTask);

    MetaTask update (MetaTask metaTask);

    Optional<MetaTask> get(long id);

    List<MetaTask> getByStatus(String status);
}

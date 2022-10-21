package dao;


import entity.MetaTask;

import java.util.List;
import java.util.Optional;

public interface MetaTaskDao {

    MetaTask insert(MetaTask metaTask);

    List<MetaTask> findByStatus(String status);

    Optional<MetaTask> findById(long id);

    MetaTask update(MetaTask metaTask);

}

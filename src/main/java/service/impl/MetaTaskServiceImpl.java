package service.impl;


import dao.MetaTaskDao;
import dao.sql.MetaTaskDaoSql;
import entity.MetaTask;
import service.MetaTaskService;

import java.util.List;
import java.util.Optional;


public class MetaTaskServiceImpl implements MetaTaskService {

    private final MetaTaskDao metaTaskDao;

    public MetaTaskServiceImpl() {
        this.metaTaskDao = new MetaTaskDaoSql();
    }

    @Override
    public MetaTask save(MetaTask metaTask) {
        return metaTaskDao.insert(metaTask);
    }

    @Override
    public MetaTask update(MetaTask metaTask) {
        return metaTaskDao.update(metaTask);
    }

    @Override
    public Optional<MetaTask> get(long id) {
        return metaTaskDao.findById(id);
    }

    @Override
    public List<MetaTask> getByStatus(String status) {
        return metaTaskDao.findByStatus(status);
    }
}

package service.impl;


import dao.ClanDao;
import dao.sql.ClanDaoSql;
import entity.Clan;
import service.ClanService;

import java.util.Optional;

public class ClanServiceImpl implements ClanService {

    private final ClanDao clanDao;

    public ClanServiceImpl() {
        this.clanDao = new ClanDaoSql();
    }

    public void createClan() {
        Optional<Clan> optionalClan = findByName("FirstClan");
        if (!optionalClan.isPresent()) {
            Clan clan = new Clan();
            clan.setName("FirstClan");
            clan.setGold(0);
            clan.setPreviousGoldCount(0);
            save(clan);
        }
    }

    @Override
    public Optional<Clan> get(long clanId) {
        return clanDao.findById(clanId);
    }

    @Override
    public Optional<Clan> findByName(String clanName) {
        return clanDao.findByName(clanName);
    }

    @Override
    public Clan save(Clan clan) {
        return clanDao.insert(clan);
    }

    @Override
    public Clan update(Clan clan) {
        return clanDao.update(clan);
    }

    @Override
    public void delete(long clanId) {
        clanDao.deleteById(clanId);
    }
}

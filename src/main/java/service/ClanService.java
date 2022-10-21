package service;


import entity.Clan;

import java.util.Optional;

public interface ClanService {

    Optional<Clan> get(long clanId);

    Optional<Clan> findByName(String name);

    Clan save(Clan clan);

    Clan update(Clan clan);

    void delete(long clanId);
}
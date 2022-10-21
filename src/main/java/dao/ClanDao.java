package dao;


import entity.Clan;

import java.util.Optional;

public interface ClanDao {

    Clan insert(Clan clan) ;

    Optional<Clan> findById(Long clanId) ;

    Optional<Clan> findByName(String name) ;

    Clan update(Clan clan) ;

    void deleteById(Long id) ;

}

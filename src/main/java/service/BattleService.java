package service;

import entity.Battle;
import events.BattleStatus;
import events.GoldAdd;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattleService {


    private final UserAddGoldService userAddGoldService;


    public void createBattle(long userId, long clanId, String status) {
        Battle battle = new Battle();
        battle.setUserId(userId);
        battle.setClanId(clanId);
        battle.setStatus(status);
        if (BattleStatus.WIN.name().equals(status)) {
            userAddGoldService.addGoldToClan(userId, clanId, 10L, GoldAdd.ADD_FOR_BATTLE);
        }
        if (BattleStatus.LOSS.name().equals(status)) {
            userAddGoldService.addGoldToClan(userId, clanId, 10L, GoldAdd.REMOVE_GOLD_FROM_CLAN);
        }
    }
}

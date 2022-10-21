package service;


import entity.MetaTask;
import entity.User;
import events.GoldAdd;
import events.MetaTaskStatus;
import service.impl.ClanServiceImpl;
import service.impl.MetaTaskServiceImpl;
import service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserAddGoldService {

    private static final Logger log = Logger.getLogger(UserAddGoldService.class.getName());

    private final ClanService clanService;
    private final UserService userService;
    private final MetaTaskService metaTaskService;

    public UserAddGoldService() {
        this.clanService = new ClanServiceImpl();
        this.userService = new UserServiceImpl();
        this.metaTaskService = new MetaTaskServiceImpl();
    }

    public void addGoldToClan(long userId, long clanId, long gold, GoldAdd goldAdd) {
        Optional<User> userOptional = userService.get(userId);
        if (userOptional.isPresent()) {
            try {
                User user = userOptional.get();
                long totalAddGold = user.getTotalAddedGold();
                if (!GoldAdd.REMOVE_GOLD_FROM_CLAN.equals(goldAdd)) {
                    user.setTotalAddedGold(totalAddGold + gold);
                    user.setLastAddedGold(gold);
                    user.setUpdated(LocalDateTime.now());
                    log.info("update user , add gold :" + user + ":" + gold);
                } else {
                    user.setTotalAddedGold(totalAddGold - gold);
                    user.setLastAddedGold(user.getLastAddedGold());
                    user.setUpdated(LocalDateTime.now());
                    log.info("update user , remove gold :" + user + ":" + gold);
                }
                userService.update(user);
            } catch (RuntimeException e) {
                log.log(Level.SEVERE, "failed update user:", e.getMessage());
            }
            try {
                MetaTask metaTask = new MetaTask();
                metaTask.setStatus(MetaTaskStatus.NEW.name());
                metaTask.setType(goldAdd.name());
                metaTask.setClanId(clanId);
                metaTask.setGold(gold);
                log.info("create metaTask :" + metaTask);
                metaTaskService.save(metaTask);
            } catch (RuntimeException e) {
                log.log(Level.SEVERE, "failed create metaTask:", e.getMessage());
            }
        } else {
            log.log(Level.SEVERE, "user by userId not found " + userId);
        }
    }
}
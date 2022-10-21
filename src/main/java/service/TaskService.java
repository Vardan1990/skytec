package service;


import entity.Clan;
import entity.MetaTask;
import events.GoldAdd;
import events.MetaTaskStatus;
import service.impl.ClanServiceImpl;
import service.impl.MetaTaskServiceImpl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class TaskService extends TimerTask {

    private static final Logger log = Logger.getLogger(TaskService.class.getName());

    private final ClanService clanService;
    private final MetaTaskService metaTaskService;

    public TaskService() {
        this.clanService = new ClanServiceImpl();
        this.metaTaskService = new MetaTaskServiceImpl();
    }

    @Override
    public void run() {
        completeTask();
    }

    private void completeTask() {
        log.info("running complete task");
        List<MetaTask> metaTaskList = metaTaskService.getByStatus(MetaTaskStatus.NEW.name())
                .stream()
                .sorted(Comparator.comparing(MetaTask::getCreated))
                .collect(Collectors.toList());
        metaTaskList.forEach(metaTask -> {
            boolean success = false;
            Optional<Clan> optionalClan = clanService.get(metaTask.getClanId());
            if (optionalClan.isPresent()) {
                Clan clan = optionalClan.get();
                long previousGoldCount = clan.getGold();
                if (!GoldAdd.REMOVE_GOLD_FROM_CLAN.name().equals(metaTask.getType())) {
                    clan.setGold(previousGoldCount + metaTask.getGold());
                } else {
                    clan.setGold(previousGoldCount - metaTask.getGold());
                }
                clan.setPreviousGoldCount(previousGoldCount);
                clan.setUpdated(LocalDateTime.now());
                clanService.update(clan);
                success = true;
            }
            if (!success) {
                metaTask.setStatus(MetaTaskStatus.FAILED.name());
                log.log(Level.SEVERE, "failed metaTask:" + metaTask.getId());
            } else {
                metaTask.setStatus(MetaTaskStatus.SUCCESS.name());
            }
            metaTask.setUpdated(LocalDateTime.now());
            metaTaskService.update(metaTask);
        });
    }

}
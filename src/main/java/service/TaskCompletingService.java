package service;

import entity.TaskComplete;
import events.GoldAdd;
import events.TaskCompletingStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskCompletingService {

    private final UserAddGoldService userAddGoldService;

    public void createDoingTask(long userId, long clanId, String status) {

        TaskComplete taskComplete = new TaskComplete();
        taskComplete.setUserId(userId);
        taskComplete.setClanId(clanId);
        taskComplete.setStatus(status);
        if (TaskCompletingStatus.COMPLETE.name().equals(status)) {
            userAddGoldService.addGoldToClan(userId, clanId, 10L, GoldAdd.ADD_FOR_TASK_COMPLETING);
        }
        if (TaskCompletingStatus.NOT_COMPLETE.name().equals(status)) {
            userAddGoldService.addGoldToClan(userId, clanId, 10L, GoldAdd.REMOVE_GOLD_FROM_CLAN);
        }
    }
}

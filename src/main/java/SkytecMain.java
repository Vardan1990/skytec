import dao.CreateDbService;
import events.BattleStatus;
import events.GoldAdd;
import events.TaskCompletingStatus;
import service.BattleService;
import service.TaskCompletingService;
import service.TaskService;
import service.UserAddGoldService;
import service.impl.ClanServiceImpl;
import service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SkytecMain {


    public static void main(String[] args) throws SQLException {

        CreateDbService service = new CreateDbService();
        service.createTable();

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUser();

        ClanServiceImpl clanService = new ClanServiceImpl();
        clanService.createClan();

        UserAddGoldService userAddGoldService = new UserAddGoldService();
        userAddGoldService.addGoldToClan(1, 1, 20, GoldAdd.ADD_FROM_POCKET);

        BattleService battleService = new BattleService(userAddGoldService);
        battleService.createBattle(1, 1, BattleStatus.WIN.name());
        battleService.createBattle(1, 1, BattleStatus.LOSS.name());

        TaskCompletingService taskCompletingService = new TaskCompletingService(userAddGoldService);
        taskCompletingService.createDoingTask(1, 1, TaskCompletingStatus.COMPLETE.name());
        taskCompletingService.createDoingTask(1, 1, TaskCompletingStatus.NOT_COMPLETE.name());

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        TaskService taskService = new TaskService();
        scheduledExecutorService.scheduleWithFixedDelay(taskService,5,5,TimeUnit.SECONDS);
    }
}

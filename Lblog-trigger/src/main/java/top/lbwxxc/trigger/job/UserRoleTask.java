package top.lbwxxc.trigger.job;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserRoleTask {

    @Scheduled(cron = "0 0 1 * * ?")
    public void PushRolePermissions2Redis() {

    }
}

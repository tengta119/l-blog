package top.lbwxxc.trigger.job;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.lbwxxc.domain.user.service.IUserInfoService;

@Component
@Slf4j
public class UserRoleTask {

    @Resource
    private IUserInfoService userService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void PushRolePermissions2Redis() {
        log.info("定时任务，将系统权限推送到 redis 中");
        userService.pushRolePermission2Redis();
    }
}

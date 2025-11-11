package top.lbwxxc.config;


import cn.dev33.satoken.stp.StpInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StpInterfaceImpl implements StpInterface {


    @Override
    public List<String> getPermissionList(Object loginId, String s) {
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object loginId, String s) {
        return List.of();
    }
}

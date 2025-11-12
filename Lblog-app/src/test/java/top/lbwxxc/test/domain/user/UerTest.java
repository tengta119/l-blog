package top.lbwxxc.test.domain.user;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lbwxxc.domain.user.service.IUserInfoService;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UerTest {

    @Resource
    private IUserInfoService userService;

    @Test
    public void test() {

        userService.pushRolePermission2Redis();
    }
}

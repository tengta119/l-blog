package top.lbwxxc.test.dao.gateway;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lbwxxc.domain.login.adapter.port.ILoginPort;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WxTest {

    @Resource
    private ILoginPort loginPort;


    @Test
    public void wxTest(){


    }
}

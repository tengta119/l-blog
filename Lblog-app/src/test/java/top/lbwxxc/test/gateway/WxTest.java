package top.lbwxxc.test.gateway;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lbwxxc.infrastructure.adapter.port.LoginPort;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WxTest {

    @Resource
    private LoginPort loginPort;

    @Test
    public void wxTest() throws IOException {
        String wxQrCodeTicket = loginPort.getWxQrCodeTicket();
        System.out.println(wxQrCodeTicket);
    }
}

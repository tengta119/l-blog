package top.lbwxxc.test.gateway;


import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lbwxxc.infrastructure.gateway.AliyunSmsHelper;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {

    @Resource
    private AliyunSmsHelper aliyunSmsHelper;

    @Test
    public void sendSmsTest() {
        // 生成 6 位随机数字验证码
        String verificationCode = RandomUtil.randomNumbers(6);

        String signName = "阿里云短信测试";
        String templateCode = "SMS_154950909";
        String templateParam = String.format("{\"code\":\"%s\"}", verificationCode);
        boolean b = aliyunSmsHelper.sendMessage(signName, templateCode, "15981160633", templateParam);
        System.out.println(b);
    }
}

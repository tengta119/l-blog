package top.lbwxxc.test.gateway;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Resource
    private JavaMailSender mailSender;


    @Test
    public void sendMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("15981160633@163.com");
        simpleMailMessage.setTo("1192299468@qq.com");
        simpleMailMessage.setSubject("验证码");
        simpleMailMessage.setText("123456");
        mailSender.send(simpleMailMessage);
    }
}

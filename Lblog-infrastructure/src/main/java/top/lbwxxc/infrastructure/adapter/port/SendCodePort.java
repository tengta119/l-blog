package top.lbwxxc.infrastructure.adapter.port;


import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.user.adapter.port.ISendCodePort;
import top.lbwxxc.infrastructure.gateway.AliyunSmsHelper;

@Service
public class SendCodePort implements ISendCodePort {

    @Resource
    private AliyunSmsHelper aliyunSmsHelper;
    @Resource
    private JavaMailSender mailSender;


    @Override
    public void sendVerificationPhoneCode(String phone, String code) {
        String signName = "阿里云短信测试";
        String templateCode = "SMS_154950909";
        String templateParam = String.format("{\"code\":\"%s\"}", code);
        aliyunSmsHelper.sendMessage(signName, templateCode, phone, templateParam);
    }

    @Override
    public void sendVerificationEmailCode(String email, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("15981160633@163.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Lblog 验证码");
        simpleMailMessage.setText(code);
        mailSender.send(simpleMailMessage);
    }
}

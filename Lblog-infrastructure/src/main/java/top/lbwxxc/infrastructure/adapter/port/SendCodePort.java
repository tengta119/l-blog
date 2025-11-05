package top.lbwxxc.infrastructure.adapter.port;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.user.adapter.port.ISendCodePort;
import top.lbwxxc.infrastructure.gateway.AliyunSmsHelper;

@Service
public class SendCodePort implements ISendCodePort {

    @Resource
    private AliyunSmsHelper aliyunSmsHelper;

    @Override
    public void sendVerificationPhoneCode(String phone, String code) {
        String signName = "阿里云短信测试";
        String templateCode = "SMS_154950909";
        String templateParam = String.format("{\"code\":\"%s\"}", code);
        aliyunSmsHelper.sendMessage(signName, templateCode, phone, templateParam);
    }
}

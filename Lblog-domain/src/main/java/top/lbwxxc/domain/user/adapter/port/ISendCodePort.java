package top.lbwxxc.domain.user.adapter.port;


import org.springframework.stereotype.Service;

@Service
public interface ISendCodePort {

    void sendVerificationPhoneCode(String phone, String code);
}
